package com.oustudents.bank3370.web.rest;

import com.oustudents.bank3370.Bank3370App;

import com.oustudents.bank3370.domain.Patron;
import com.oustudents.bank3370.repository.PatronRepository;
import com.oustudents.bank3370.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.oustudents.bank3370.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PatronResource REST controller.
 *
 * @see PatronResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bank3370App.class)
public class PatronResourceIntTest {

    private static final String DEFAULT_PATRON_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATRON_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATRON_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATRON_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATRON_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PATRON_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPatronMockMvc;

    private Patron patron;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatronResource patronResource = new PatronResource(patronRepository);
        this.restPatronMockMvc = MockMvcBuilders.standaloneSetup(patronResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patron createEntity(EntityManager em) {
        Patron patron = new Patron()
            .patronFirstName(DEFAULT_PATRON_FIRST_NAME)
            .patronLastName(DEFAULT_PATRON_LAST_NAME)
            .patronEmail(DEFAULT_PATRON_EMAIL);
        return patron;
    }

    @Before
    public void initTest() {
        patron = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatron() throws Exception {
        int databaseSizeBeforeCreate = patronRepository.findAll().size();

        // Create the Patron
        restPatronMockMvc.perform(post("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isCreated());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate + 1);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getPatronFirstName()).isEqualTo(DEFAULT_PATRON_FIRST_NAME);
        assertThat(testPatron.getPatronLastName()).isEqualTo(DEFAULT_PATRON_LAST_NAME);
        assertThat(testPatron.getPatronEmail()).isEqualTo(DEFAULT_PATRON_EMAIL);
    }

    @Test
    @Transactional
    public void createPatronWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patronRepository.findAll().size();

        // Create the Patron with an existing ID
        patron.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatronMockMvc.perform(post("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPatronFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patronRepository.findAll().size();
        // set the field null
        patron.setPatronFirstName(null);

        // Create the Patron, which fails.

        restPatronMockMvc.perform(post("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPatronLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patronRepository.findAll().size();
        // set the field null
        patron.setPatronLastName(null);

        // Create the Patron, which fails.

        restPatronMockMvc.perform(post("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatrons() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get all the patronList
        restPatronMockMvc.perform(get("/api/patrons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patron.getId().intValue())))
            .andExpect(jsonPath("$.[*].patronFirstName").value(hasItem(DEFAULT_PATRON_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].patronLastName").value(hasItem(DEFAULT_PATRON_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].patronEmail").value(hasItem(DEFAULT_PATRON_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getPatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get the patron
        restPatronMockMvc.perform(get("/api/patrons/{id}", patron.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patron.getId().intValue()))
            .andExpect(jsonPath("$.patronFirstName").value(DEFAULT_PATRON_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.patronLastName").value(DEFAULT_PATRON_LAST_NAME.toString()))
            .andExpect(jsonPath("$.patronEmail").value(DEFAULT_PATRON_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPatron() throws Exception {
        // Get the patron
        restPatronMockMvc.perform(get("/api/patrons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron
        Patron updatedPatron = patronRepository.findById(patron.getId()).get();
        // Disconnect from session so that the updates on updatedPatron are not directly saved in db
        em.detach(updatedPatron);
        updatedPatron
            .patronFirstName(UPDATED_PATRON_FIRST_NAME)
            .patronLastName(UPDATED_PATRON_LAST_NAME)
            .patronEmail(UPDATED_PATRON_EMAIL);

        restPatronMockMvc.perform(put("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatron)))
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getPatronFirstName()).isEqualTo(UPDATED_PATRON_FIRST_NAME);
        assertThat(testPatron.getPatronLastName()).isEqualTo(UPDATED_PATRON_LAST_NAME);
        assertThat(testPatron.getPatronEmail()).isEqualTo(UPDATED_PATRON_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Create the Patron

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatronMockMvc.perform(put("/api/patrons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeDelete = patronRepository.findAll().size();

        // Get the patron
        restPatronMockMvc.perform(delete("/api/patrons/{id}", patron.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patron.class);
        Patron patron1 = new Patron();
        patron1.setId(1L);
        Patron patron2 = new Patron();
        patron2.setId(patron1.getId());
        assertThat(patron1).isEqualTo(patron2);
        patron2.setId(2L);
        assertThat(patron1).isNotEqualTo(patron2);
        patron1.setId(null);
        assertThat(patron1).isNotEqualTo(patron2);
    }
}
