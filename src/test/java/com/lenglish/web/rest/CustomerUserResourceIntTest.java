package com.lenglish.web.rest;

import com.lenglish.LenglishApp;

import com.lenglish.domain.CustomerUser;
import com.lenglish.repository.CustomerUserRepository;
import com.lenglish.service.CustomerUserService;
import com.lenglish.service.dto.CustomerUserDTO;
import com.lenglish.service.mapper.CustomerUserMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CustomerUserResource REST controller.
 *
 * @see CustomerUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LenglishApp.class)
public class CustomerUserResourceIntTest {

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_POINT = 1;
    private static final Integer UPDATED_POINT = 2;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Integer DEFAULT_TODAY_POINT = 1;
    private static final Integer UPDATED_TODAY_POINT = 2;

    private static final Integer DEFAULT_DATE_GOAL = 1;
    private static final Integer UPDATED_DATE_GOAL = 2;

    @Autowired
    private CustomerUserRepository customerUserRepository;

    @Autowired
    private CustomerUserMapper customerUserMapper;

    @Autowired
    private CustomerUserService customerUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCustomerUserMockMvc;

    private CustomerUser customerUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerUserResource customerUserResource = new CustomerUserResource(customerUserService, null);
        this.restCustomerUserMockMvc = MockMvcBuilders.standaloneSetup(customerUserResource)
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
    public static CustomerUser createEntity(EntityManager em) {
        CustomerUser customerUser = new CustomerUser()
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .point(DEFAULT_POINT)
            .level(DEFAULT_LEVEL)
            .todayPoint(DEFAULT_TODAY_POINT)
            .dateGoal(DEFAULT_DATE_GOAL);
        return customerUser;
    }

    @Before
    public void initTest() {
        customerUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerUser() throws Exception {
        int databaseSizeBeforeCreate = customerUserRepository.findAll().size();

        // Create the CustomerUser
        CustomerUserDTO customerUserDTO = customerUserMapper.toDto(customerUser);
        restCustomerUserMockMvc.perform(post("/api/customer-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerUser in the database
        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerUser testCustomerUser = customerUserList.get(customerUserList.size() - 1);
        assertThat(testCustomerUser.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testCustomerUser.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testCustomerUser.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testCustomerUser.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCustomerUser.getTodayPoint()).isEqualTo(DEFAULT_TODAY_POINT);
        assertThat(testCustomerUser.getDateGoal()).isEqualTo(DEFAULT_DATE_GOAL);
    }

    @Test
    @Transactional
    public void createCustomerUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerUserRepository.findAll().size();

        // Create the CustomerUser with an existing ID
        customerUser.setId(1L);
        CustomerUserDTO customerUserDTO = customerUserMapper.toDto(customerUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerUserMockMvc.perform(post("/api/customer-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerUser in the database
        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerUserRepository.findAll().size();
        // set the field null
        customerUser.setPoint(null);

        // Create the CustomerUser, which fails.
        CustomerUserDTO customerUserDTO = customerUserMapper.toDto(customerUser);

        restCustomerUserMockMvc.perform(post("/api/customer-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerUserDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerUserRepository.findAll().size();
        // set the field null
        customerUser.setLevel(null);

        // Create the CustomerUser, which fails.
        CustomerUserDTO customerUserDTO = customerUserMapper.toDto(customerUser);

        restCustomerUserMockMvc.perform(post("/api/customer-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerUserDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerUsers() throws Exception {
        // Initialize the database
        customerUserRepository.saveAndFlush(customerUser);

        // Get all the customerUserList
        restCustomerUserMockMvc.perform(get("/api/customer-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].todayPoint").value(hasItem(DEFAULT_TODAY_POINT)))
            .andExpect(jsonPath("$.[*].dateGoal").value(hasItem(DEFAULT_DATE_GOAL)));
    }

    @Test
    @Transactional
    public void getCustomerUser() throws Exception {
        // Initialize the database
        customerUserRepository.saveAndFlush(customerUser);

        // Get the customerUser
        restCustomerUserMockMvc.perform(get("/api/customer-users/{id}", customerUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerUser.getId().intValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.todayPoint").value(DEFAULT_TODAY_POINT))
            .andExpect(jsonPath("$.dateGoal").value(DEFAULT_DATE_GOAL));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerUser() throws Exception {
        // Get the customerUser
        restCustomerUserMockMvc.perform(get("/api/customer-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerUser() throws Exception {
        // Initialize the database
        customerUserRepository.saveAndFlush(customerUser);
        int databaseSizeBeforeUpdate = customerUserRepository.findAll().size();

        // Update the customerUser
        CustomerUser updatedCustomerUser = customerUserRepository.findOne(customerUser.getId());
        updatedCustomerUser
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .point(UPDATED_POINT)
            .level(UPDATED_LEVEL)
            .todayPoint(UPDATED_TODAY_POINT)
            .dateGoal(UPDATED_DATE_GOAL);
        CustomerUserDTO customerUserDTO = customerUserMapper.toDto(updatedCustomerUser);

        restCustomerUserMockMvc.perform(put("/api/customer-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerUserDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerUser in the database
        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeUpdate);
        CustomerUser testCustomerUser = customerUserList.get(customerUserList.size() - 1);
        assertThat(testCustomerUser.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testCustomerUser.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testCustomerUser.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testCustomerUser.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCustomerUser.getTodayPoint()).isEqualTo(UPDATED_TODAY_POINT);
        assertThat(testCustomerUser.getDateGoal()).isEqualTo(UPDATED_DATE_GOAL);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerUser() throws Exception {
        int databaseSizeBeforeUpdate = customerUserRepository.findAll().size();

        // Create the CustomerUser
        CustomerUserDTO customerUserDTO = customerUserMapper.toDto(customerUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCustomerUserMockMvc.perform(put("/api/customer-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerUserDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerUser in the database
        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCustomerUser() throws Exception {
        // Initialize the database
        customerUserRepository.saveAndFlush(customerUser);
        int databaseSizeBeforeDelete = customerUserRepository.findAll().size();

        // Get the customerUser
        restCustomerUserMockMvc.perform(delete("/api/customer-users/{id}", customerUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomerUser> customerUserList = customerUserRepository.findAll();
        assertThat(customerUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerUser.class);
        CustomerUser customerUser1 = new CustomerUser();
        customerUser1.setId(1L);
        CustomerUser customerUser2 = new CustomerUser();
        customerUser2.setId(customerUser1.getId());
        assertThat(customerUser1).isEqualTo(customerUser2);
        customerUser2.setId(2L);
        assertThat(customerUser1).isNotEqualTo(customerUser2);
        customerUser1.setId(null);
        assertThat(customerUser1).isNotEqualTo(customerUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerUserDTO.class);
        CustomerUserDTO customerUserDTO1 = new CustomerUserDTO();
        customerUserDTO1.setId(1L);
        CustomerUserDTO customerUserDTO2 = new CustomerUserDTO();
        assertThat(customerUserDTO1).isNotEqualTo(customerUserDTO2);
        customerUserDTO2.setId(customerUserDTO1.getId());
        assertThat(customerUserDTO1).isEqualTo(customerUserDTO2);
        customerUserDTO2.setId(2L);
        assertThat(customerUserDTO1).isNotEqualTo(customerUserDTO2);
        customerUserDTO1.setId(null);
        assertThat(customerUserDTO1).isNotEqualTo(customerUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerUserMapper.fromId(null)).isNull();
    }
}
