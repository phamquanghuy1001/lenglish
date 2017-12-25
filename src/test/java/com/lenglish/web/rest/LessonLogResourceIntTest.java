package com.lenglish.web.rest;

import com.lenglish.LenglishApp;

import com.lenglish.domain.LessonLog;
import com.lenglish.repository.LessonLogRepository;
import com.lenglish.service.LessonLogService;
import com.lenglish.service.dto.LessonLogDTO;
import com.lenglish.service.mapper.LessonLogMapper;
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
 * Test class for the LessonLogResource REST controller.
 *
 * @see LessonLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenglishApp.class)
public class LessonLogResourceIntTest {

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_COMPLETE = 0;
    private static final Integer UPDATED_COMPLETE = 1;

    private static final Integer DEFAULT_TRANSLATION = 0;
    private static final Integer UPDATED_TRANSLATION = 1;

    private static final Integer DEFAULT_LISTENING = 0;
    private static final Integer UPDATED_LISTENING = 1;

    private static final Integer DEFAULT_SELECTION = 0;
    private static final Integer UPDATED_SELECTION = 1;

    private static final Integer DEFAULT_SPEECH = 0;
    private static final Integer UPDATED_SPEECH = 1;

    @Autowired
    private LessonLogRepository lessonLogRepository;

    @Autowired
    private LessonLogMapper lessonLogMapper;

    @Autowired
    private LessonLogService lessonLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLessonLogMockMvc;

    private LessonLog lessonLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LessonLogResource lessonLogResource = new LessonLogResource(lessonLogService, null);
        this.restLessonLogMockMvc = MockMvcBuilders.standaloneSetup(lessonLogResource)
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
    public static LessonLog createEntity(EntityManager em) {
        LessonLog lessonLog = new LessonLog()
            .createDate(DEFAULT_CREATE_DATE)
            .complete(DEFAULT_COMPLETE)
            .translation(DEFAULT_TRANSLATION)
            .listening(DEFAULT_LISTENING)
            .selection(DEFAULT_SELECTION)
            .speech(DEFAULT_SPEECH);
        return lessonLog;
    }

    @Before
    public void initTest() {
        lessonLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createLessonLog() throws Exception {
        int databaseSizeBeforeCreate = lessonLogRepository.findAll().size();

        // Create the LessonLog
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);
        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isCreated());

        // Validate the LessonLog in the database
        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeCreate + 1);
        LessonLog testLessonLog = lessonLogList.get(lessonLogList.size() - 1);
        assertThat(testLessonLog.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testLessonLog.getComplete()).isEqualTo(DEFAULT_COMPLETE);
        assertThat(testLessonLog.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
        assertThat(testLessonLog.getListening()).isEqualTo(DEFAULT_LISTENING);
        assertThat(testLessonLog.getSelection()).isEqualTo(DEFAULT_SELECTION);
        assertThat(testLessonLog.getSpeech()).isEqualTo(DEFAULT_SPEECH);
    }

    @Test
    @Transactional
    public void createLessonLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lessonLogRepository.findAll().size();

        // Create the LessonLog with an existing ID
        lessonLog.setId(1L);
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LessonLog in the database
        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCompleteIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonLogRepository.findAll().size();
        // set the field null
        lessonLog.setComplete(null);

        // Create the LessonLog, which fails.
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isBadRequest());

        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTranslationIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonLogRepository.findAll().size();
        // set the field null
        lessonLog.setTranslation(null);

        // Create the LessonLog, which fails.
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isBadRequest());

        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkListeningIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonLogRepository.findAll().size();
        // set the field null
        lessonLog.setListening(null);

        // Create the LessonLog, which fails.
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isBadRequest());

        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSelectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonLogRepository.findAll().size();
        // set the field null
        lessonLog.setSelection(null);

        // Create the LessonLog, which fails.
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isBadRequest());

        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeechIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonLogRepository.findAll().size();
        // set the field null
        lessonLog.setSpeech(null);

        // Create the LessonLog, which fails.
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        restLessonLogMockMvc.perform(post("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isBadRequest());

        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLessonLogs() throws Exception {
        // Initialize the database
        lessonLogRepository.saveAndFlush(lessonLog);

        // Get all the lessonLogList
        restLessonLogMockMvc.perform(get("/api/lesson-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lessonLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(sameInstant(DEFAULT_CREATE_DATE))))
            .andExpect(jsonPath("$.[*].complete").value(hasItem(DEFAULT_COMPLETE)))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION)))
            .andExpect(jsonPath("$.[*].listening").value(hasItem(DEFAULT_LISTENING)))
            .andExpect(jsonPath("$.[*].selection").value(hasItem(DEFAULT_SELECTION)))
            .andExpect(jsonPath("$.[*].speech").value(hasItem(DEFAULT_SPEECH)));
    }

    @Test
    @Transactional
    public void getLessonLog() throws Exception {
        // Initialize the database
        lessonLogRepository.saveAndFlush(lessonLog);

        // Get the lessonLog
        restLessonLogMockMvc.perform(get("/api/lesson-logs/{id}", lessonLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lessonLog.getId().intValue()))
            .andExpect(jsonPath("$.createDate").value(sameInstant(DEFAULT_CREATE_DATE)))
            .andExpect(jsonPath("$.complete").value(DEFAULT_COMPLETE))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION))
            .andExpect(jsonPath("$.listening").value(DEFAULT_LISTENING))
            .andExpect(jsonPath("$.selection").value(DEFAULT_SELECTION))
            .andExpect(jsonPath("$.speech").value(DEFAULT_SPEECH));
    }

    @Test
    @Transactional
    public void getNonExistingLessonLog() throws Exception {
        // Get the lessonLog
        restLessonLogMockMvc.perform(get("/api/lesson-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLessonLog() throws Exception {
        // Initialize the database
        lessonLogRepository.saveAndFlush(lessonLog);
        int databaseSizeBeforeUpdate = lessonLogRepository.findAll().size();

        // Update the lessonLog
        LessonLog updatedLessonLog = lessonLogRepository.findOne(lessonLog.getId());
        updatedLessonLog
            .createDate(UPDATED_CREATE_DATE)
            .complete(UPDATED_COMPLETE)
            .translation(UPDATED_TRANSLATION)
            .listening(UPDATED_LISTENING)
            .selection(UPDATED_SELECTION)
            .speech(UPDATED_SPEECH);
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(updatedLessonLog);

        restLessonLogMockMvc.perform(put("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isOk());

        // Validate the LessonLog in the database
        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeUpdate);
        LessonLog testLessonLog = lessonLogList.get(lessonLogList.size() - 1);
        assertThat(testLessonLog.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testLessonLog.getComplete()).isEqualTo(UPDATED_COMPLETE);
        assertThat(testLessonLog.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
        assertThat(testLessonLog.getListening()).isEqualTo(UPDATED_LISTENING);
        assertThat(testLessonLog.getSelection()).isEqualTo(UPDATED_SELECTION);
        assertThat(testLessonLog.getSpeech()).isEqualTo(UPDATED_SPEECH);
    }

    @Test
    @Transactional
    public void updateNonExistingLessonLog() throws Exception {
        int databaseSizeBeforeUpdate = lessonLogRepository.findAll().size();

        // Create the LessonLog
        LessonLogDTO lessonLogDTO = lessonLogMapper.toDto(lessonLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLessonLogMockMvc.perform(put("/api/lesson-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonLogDTO)))
            .andExpect(status().isCreated());

        // Validate the LessonLog in the database
        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLessonLog() throws Exception {
        // Initialize the database
        lessonLogRepository.saveAndFlush(lessonLog);
        int databaseSizeBeforeDelete = lessonLogRepository.findAll().size();

        // Get the lessonLog
        restLessonLogMockMvc.perform(delete("/api/lesson-logs/{id}", lessonLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LessonLog> lessonLogList = lessonLogRepository.findAll();
        assertThat(lessonLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonLog.class);
        LessonLog lessonLog1 = new LessonLog();
        lessonLog1.setId(1L);
        LessonLog lessonLog2 = new LessonLog();
        lessonLog2.setId(lessonLog1.getId());
        assertThat(lessonLog1).isEqualTo(lessonLog2);
        lessonLog2.setId(2L);
        assertThat(lessonLog1).isNotEqualTo(lessonLog2);
        lessonLog1.setId(null);
        assertThat(lessonLog1).isNotEqualTo(lessonLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonLogDTO.class);
        LessonLogDTO lessonLogDTO1 = new LessonLogDTO();
        lessonLogDTO1.setId(1L);
        LessonLogDTO lessonLogDTO2 = new LessonLogDTO();
        assertThat(lessonLogDTO1).isNotEqualTo(lessonLogDTO2);
        lessonLogDTO2.setId(lessonLogDTO1.getId());
        assertThat(lessonLogDTO1).isEqualTo(lessonLogDTO2);
        lessonLogDTO2.setId(2L);
        assertThat(lessonLogDTO1).isNotEqualTo(lessonLogDTO2);
        lessonLogDTO1.setId(null);
        assertThat(lessonLogDTO1).isNotEqualTo(lessonLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lessonLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lessonLogMapper.fromId(null)).isNull();
    }
}
