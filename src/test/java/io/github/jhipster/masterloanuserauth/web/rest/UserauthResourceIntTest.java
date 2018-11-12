package io.github.jhipster.masterloanuserauth.web.rest;

import io.github.jhipster.masterloanuserauth.MasterLoanUserAuthApp;

import io.github.jhipster.masterloanuserauth.domain.Userauth;
import io.github.jhipster.masterloanuserauth.repository.UserauthRepository;
import io.github.jhipster.masterloanuserauth.repository.search.UserauthSearchRepository;
import io.github.jhipster.masterloanuserauth.service.UserauthService;
import io.github.jhipster.masterloanuserauth.web.rest.errors.ExceptionTranslator;

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
import java.util.Collections;
import java.util.List;


import static io.github.jhipster.masterloanuserauth.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserauthResource REST controller.
 *
 * @see UserauthResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MasterLoanUserAuthApp.class)
public class UserauthResourceIntTest {

    private static final Long DEFAULT_CEDULA_CUSTOMER = 1L;
    private static final Long UPDATED_CEDULA_CUSTOMER = 2L;

    private static final String DEFAULT_TYPE_ID_CUSTOMER = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_ID_CUSTOMER = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private UserauthRepository userauthRepository;

    @Autowired
    private UserauthService userauthService;

    /**
     * This repository is mocked in the io.github.jhipster.masterloanuserauth.repository.search test package.
     *
     * @see io.github.jhipster.masterloanuserauth.repository.search.UserauthSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserauthSearchRepository mockUserauthSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserauthMockMvc;

    private Userauth userauth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserauthResource userauthResource = new UserauthResource(userauthService);
        this.restUserauthMockMvc = MockMvcBuilders.standaloneSetup(userauthResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userauth createEntity(EntityManager em) {
        Userauth userauth = new Userauth()
            .cedulaCustomer(DEFAULT_CEDULA_CUSTOMER)
            .typeIdCustomer(DEFAULT_TYPE_ID_CUSTOMER)
            .userName(DEFAULT_USER_NAME)
            .password(DEFAULT_PASSWORD);
        return userauth;
    }

    @Before
    public void initTest() {
        userauth = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserauth() throws Exception {
        int databaseSizeBeforeCreate = userauthRepository.findAll().size();

        // Create the Userauth
        restUserauthMockMvc.perform(post("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isCreated());

        // Validate the Userauth in the database
        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeCreate + 1);
        Userauth testUserauth = userauthList.get(userauthList.size() - 1);
        assertThat(testUserauth.getCedulaCustomer()).isEqualTo(DEFAULT_CEDULA_CUSTOMER);
        assertThat(testUserauth.getTypeIdCustomer()).isEqualTo(DEFAULT_TYPE_ID_CUSTOMER);
        assertThat(testUserauth.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserauth.getPassword()).isEqualTo(DEFAULT_PASSWORD);

        // Validate the Userauth in Elasticsearch
        verify(mockUserauthSearchRepository, times(1)).save(testUserauth);
    }

    @Test
    @Transactional
    public void createUserauthWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userauthRepository.findAll().size();

        // Create the Userauth with an existing ID
        userauth.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserauthMockMvc.perform(post("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isBadRequest());

        // Validate the Userauth in the database
        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeCreate);

        // Validate the Userauth in Elasticsearch
        verify(mockUserauthSearchRepository, times(0)).save(userauth);
    }

    @Test
    @Transactional
    public void checkCedulaCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = userauthRepository.findAll().size();
        // set the field null
        userauth.setCedulaCustomer(null);

        // Create the Userauth, which fails.

        restUserauthMockMvc.perform(post("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isBadRequest());

        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIdCustomerIsRequired() throws Exception {
        int databaseSizeBeforeTest = userauthRepository.findAll().size();
        // set the field null
        userauth.setTypeIdCustomer(null);

        // Create the Userauth, which fails.

        restUserauthMockMvc.perform(post("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isBadRequest());

        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userauthRepository.findAll().size();
        // set the field null
        userauth.setUserName(null);

        // Create the Userauth, which fails.

        restUserauthMockMvc.perform(post("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isBadRequest());

        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userauthRepository.findAll().size();
        // set the field null
        userauth.setPassword(null);

        // Create the Userauth, which fails.

        restUserauthMockMvc.perform(post("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isBadRequest());

        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserauths() throws Exception {
        // Initialize the database
        userauthRepository.saveAndFlush(userauth);

        // Get all the userauthList
        restUserauthMockMvc.perform(get("/api/userauths?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userauth.getId().intValue())))
            .andExpect(jsonPath("$.[*].cedulaCustomer").value(hasItem(DEFAULT_CEDULA_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].typeIdCustomer").value(hasItem(DEFAULT_TYPE_ID_CUSTOMER.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }
    
    @Test
    @Transactional
    public void getUserauth() throws Exception {
        // Initialize the database
        userauthRepository.saveAndFlush(userauth);

        // Get the userauth
        restUserauthMockMvc.perform(get("/api/userauths/{id}", userauth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userauth.getId().intValue()))
            .andExpect(jsonPath("$.cedulaCustomer").value(DEFAULT_CEDULA_CUSTOMER.intValue()))
            .andExpect(jsonPath("$.typeIdCustomer").value(DEFAULT_TYPE_ID_CUSTOMER.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserauth() throws Exception {
        // Get the userauth
        restUserauthMockMvc.perform(get("/api/userauths/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserauth() throws Exception {
        // Initialize the database
        userauthService.save(userauth);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockUserauthSearchRepository);

        int databaseSizeBeforeUpdate = userauthRepository.findAll().size();

        // Update the userauth
        Userauth updatedUserauth = userauthRepository.findById(userauth.getId()).get();
        // Disconnect from session so that the updates on updatedUserauth are not directly saved in db
        em.detach(updatedUserauth);
        updatedUserauth
            .cedulaCustomer(UPDATED_CEDULA_CUSTOMER)
            .typeIdCustomer(UPDATED_TYPE_ID_CUSTOMER)
            .userName(UPDATED_USER_NAME)
            .password(UPDATED_PASSWORD);

        restUserauthMockMvc.perform(put("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserauth)))
            .andExpect(status().isOk());

        // Validate the Userauth in the database
        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeUpdate);
        Userauth testUserauth = userauthList.get(userauthList.size() - 1);
        assertThat(testUserauth.getCedulaCustomer()).isEqualTo(UPDATED_CEDULA_CUSTOMER);
        assertThat(testUserauth.getTypeIdCustomer()).isEqualTo(UPDATED_TYPE_ID_CUSTOMER);
        assertThat(testUserauth.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserauth.getPassword()).isEqualTo(UPDATED_PASSWORD);

        // Validate the Userauth in Elasticsearch
        verify(mockUserauthSearchRepository, times(1)).save(testUserauth);
    }

    @Test
    @Transactional
    public void updateNonExistingUserauth() throws Exception {
        int databaseSizeBeforeUpdate = userauthRepository.findAll().size();

        // Create the Userauth

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserauthMockMvc.perform(put("/api/userauths")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userauth)))
            .andExpect(status().isBadRequest());

        // Validate the Userauth in the database
        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Userauth in Elasticsearch
        verify(mockUserauthSearchRepository, times(0)).save(userauth);
    }

    @Test
    @Transactional
    public void deleteUserauth() throws Exception {
        // Initialize the database
        userauthService.save(userauth);

        int databaseSizeBeforeDelete = userauthRepository.findAll().size();

        // Get the userauth
        restUserauthMockMvc.perform(delete("/api/userauths/{id}", userauth.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Userauth> userauthList = userauthRepository.findAll();
        assertThat(userauthList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Userauth in Elasticsearch
        verify(mockUserauthSearchRepository, times(1)).deleteById(userauth.getId());
    }

    @Test
    @Transactional
    public void searchUserauth() throws Exception {
        // Initialize the database
        userauthService.save(userauth);
        when(mockUserauthSearchRepository.search(queryStringQuery("id:" + userauth.getId())))
            .thenReturn(Collections.singletonList(userauth));
        // Search the userauth
        restUserauthMockMvc.perform(get("/api/_search/userauths?query=id:" + userauth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userauth.getId().intValue())))
            .andExpect(jsonPath("$.[*].cedulaCustomer").value(hasItem(DEFAULT_CEDULA_CUSTOMER.intValue())))
            .andExpect(jsonPath("$.[*].typeIdCustomer").value(hasItem(DEFAULT_TYPE_ID_CUSTOMER)))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Userauth.class);
        Userauth userauth1 = new Userauth();
        userauth1.setId(1L);
        Userauth userauth2 = new Userauth();
        userauth2.setId(userauth1.getId());
        assertThat(userauth1).isEqualTo(userauth2);
        userauth2.setId(2L);
        assertThat(userauth1).isNotEqualTo(userauth2);
        userauth1.setId(null);
        assertThat(userauth1).isNotEqualTo(userauth2);
    }
}
