package com.lenglish.web.rest;

import com.lenglish.LenglishApp;

import com.lenglish.domain.ExamLog;
import com.lenglish.repository.ExamLogRepository;
import com.lenglish.service.ExamLogService;
import com.lenglish.service.dto.ExamLogDTO;
import com.lenglish.service.mapper.ExamLogMapper;
import com.lenglish.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.lenglish.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExamLogResource REST controller.
 *
 * @see ExamLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenglishApp.class)
public class ExamLogResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_COMPLETE = 0;
    private static final Integer UPDATED_COMPLETE = 1;

    @Autowired
    private ExamLogRepository examLogRepository;

    @Autowired
    private ExamLogMapper examLogMapper;

    @Autowired
    private ExamLogService examLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamLogMockMvc;

    private ExamLog examLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamLogResource examLogResource = new ExamLogResource(examLogService);
        this.restExamLogMockMvc = MockMvcBuilders.standaloneSetup(examLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExamLog createEntity(EntityManager em) {
        ExamLog examLog = new ExamLog()
            .createDate(DEFAULT_CREATE_DATE)
            .complete(DEFAULT_COMPLETE);
        return examLog;
    }

    @Before
    public void initTest() {
        examLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamLog() throws Exception {
        int databaseSizeBeforeCreate = examLogRepository.findAll().size();

        // Create the ExamLog
        ExamLogDTO examLogDTO = examLogMapper.toDto(examLog);
        restExamLogMockMvc.perform(post("/api/exam-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examLogDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamLog in the database
        List<ExamLog> examLogList = examLogRepository.findAll();
        assertThat(examLogList).hasSize(databaseSizeBeforeCreate + 1);
        ExamLog testExamLog = examLogList.get(examLogList.size() - 1);
        assertThat(testExamLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testExamLog.getComplete()).isEqualTo(DEFAULT_COMPLETE);
    }

    @Test
    @Transactional
    public void createExamLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examLogRepository.findAll().size();

        // Create the ExamLog with an existing ID
        examLog.setId(1L);
        ExamLogDTO examLogDTO = examLogMapper.toDto(examLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamLogMockMvc.perform(post("/api/exam-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamLog in the database
        List<ExamLog> examLogList = examLogRepository.findAll();
        assertThat(examLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCompleteIsRequired() throws Exception {
        int databaseSizeBeforeTest = examLogRepository.findAll().size();
        // set the field null
        examLog.setComplete(null);

        // Create the ExamLog, which fails.
        ExamLogDTO examLogDTO = examLogMapper.toDto(examLog);

        restExamLogMockMvc.perform(post("/api/exam-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examLogDTO)))
            .andExpect(status().isBadRequest());

        List<ExamLog> examLogList = examLogRepository.findAll();
        assertThat(examLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExamLogs() throws Exception {
        // Initialize the database
        examLogRepository.saveAndFlush(examLog);

        // Get all the examLogList
        restExamLogMockMvc.perform(get("/api/exam-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE)));
    }

    @Test
    @Transactional
    public void getExamLog() throws Exception {
        // Initialize the database
        examLogRepository.saveAndFlush(examLog);

        // Get the examLog
        restExamLogMockMvc.perform(get("/api/exam-logs/{id}", examLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examLog.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE));
    }

    @Test
    @Transactional
    public void getNonExistingExamLog() throws Exception {
        // Get the examLog
        restExamLogMockMvc.perform(get("/api/exam-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamLog() throws Exception {
        // Initialize the database
        examLogRepository.saveAndFlush(examLog);
        int databaseSizeBeforeUpdate = examLogRepository.findAll().size();

        // Update the examLog
        ExamLog updatedExamLog = examLogRepository.findOne(examLog.getId());
        updatedExamLog
            .createDate(UPDATED_CREATE_DATE)
            .complete(UPDATED_COMPLETE);
        ExamLogDTO examLogDTO = examLogMapper.toDto(updatedExamLog);

        restExamLogMockMvc.perform(put("/api/exam-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examLogDTO)))
            .andExpect(status().isOk());

        // Validate the ExamLog in the database
        List<ExamLog> examLogList = examLogRepository.findAll();
        assertThat(examLogList).hasSize(databaseSizeBeforeUpdate);
        ExamLog testExamLog = examLogList.get(examLogList.size() - 1);
        assertThat(testExamLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testExamLog.getComplete()).isEqualTo(UPDATED_COMPLETE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamLog() throws Exception {
        int databaseSizeBeforeUpdate = examLogRepository.findAll().size();

        // Create the ExamLog
        ExamLogDTO examLogDTO = examLogMapper.toDto(examLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamLogMockMvc.perform(put("/api/exam-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examLogDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamLog in the database
        List<ExamLog> examLogList = examLogRepository.findAll();
        assertThat(examLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamLog() throws Exception {
        // Initialize the database
        examLogRepository.saveAndFlush(examLog);
        int databaseSizeBeforeDelete = examLogRepository.findAll().size();

        // Get the examLog
        restExamLogMockMvc.perform(delete("/api/exam-logs/{id}", examLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamLog> examLogList = examLogRepository.findAll();
        assertThat(examLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamLog.class);
        ExamLog examLog1 = new ExamLog();
        examLog1.setId(1L);
        ExamLog examLog2 = new ExamLog();
        examLog2.setId(examLog1.getId());
        assertThat(examLog1).isEqualTo(examLog2);
        examLog2.setId(2L);
        assertThat(examLog1).isNotEqualTo(examLog2);
        examLog1.setId(null);
        assertThat(examLog1).isNotEqualTo(examLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamLogDTO.class);
        ExamLogDTO examLogDTO1 = new ExamLogDTO();
        examLogDTO1.setId(1L);
        ExamLogDTO examLogDTO2 = new ExamLogDTO();
        assertThat(examLogDTO1).isNotEqualTo(examLogDTO2);
        examLogDTO2.setId(examLogDTO1.getId());
        assertThat(examLogDTO1).isEqualTo(examLogDTO2);
        examLogDTO2.setId(2L);
        assertThat(examLogDTO1).isNotEqualTo(examLogDTO2);
        examLogDTO1.setId(null);
        assertThat(examLogDTO1).isNotEqualTo(examLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examLogMapper.fromId(null)).isNull();
    }
}
