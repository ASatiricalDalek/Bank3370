package com.oustudents.bank3370.web.rest;

import com.oustudents.bank3370.Bank3370App;

import com.oustudents.bank3370.domain.LoanType;
import com.oustudents.bank3370.repository.LoanTypeRepository;
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
 * Test class for the LoanTypeResource REST controller.
 *
 * @see LoanTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bank3370App.class)
public class LoanTypeResourceIntTest {

    private static final String DEFAULT_LOAN_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_CATEGORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_INTEREST_RATE = 1;
    private static final Integer UPDATED_INTEREST_RATE = 2;

    @Autowired
    private LoanTypeRepository loanTypeRepository;

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

    private MockMvc restLoanTypeMockMvc;

    private LoanType loanType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoanTypeResource loanTypeResource = new LoanTypeResource(loanTypeRepository);
        this.restLoanTypeMockMvc = MockMvcBuilders.standaloneSetup(loanTypeResource)
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
    public static LoanType createEntity(EntityManager em) {
        LoanType loanType = new LoanType()
            .loanCategory(DEFAULT_LOAN_CATEGORY)
            .interestRate(DEFAULT_INTEREST_RATE);
        return loanType;
    }

    @Before
    public void initTest() {
        loanType = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoanType() throws Exception {
        int databaseSizeBeforeCreate = loanTypeRepository.findAll().size();

        // Create the LoanType
        restLoanTypeMockMvc.perform(post("/api/loan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isCreated());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeCreate + 1);
        LoanType testLoanType = loanTypeList.get(loanTypeList.size() - 1);
        assertThat(testLoanType.getLoanCategory()).isEqualTo(DEFAULT_LOAN_CATEGORY);
        assertThat(testLoanType.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
    }

    @Test
    @Transactional
    public void createLoanTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loanTypeRepository.findAll().size();

        // Create the LoanType with an existing ID
        loanType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanTypeMockMvc.perform(post("/api/loan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoanCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanTypeRepository.findAll().size();
        // set the field null
        loanType.setLoanCategory(null);

        // Create the LoanType, which fails.

        restLoanTypeMockMvc.perform(post("/api/loan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isBadRequest());

        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInterestRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loanTypeRepository.findAll().size();
        // set the field null
        loanType.setInterestRate(null);

        // Create the LoanType, which fails.

        restLoanTypeMockMvc.perform(post("/api/loan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isBadRequest());

        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoanTypes() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        // Get all the loanTypeList
        restLoanTypeMockMvc.perform(get("/api/loan-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanType.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanCategory").value(hasItem(DEFAULT_LOAN_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE)));
    }
    
    @Test
    @Transactional
    public void getLoanType() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        // Get the loanType
        restLoanTypeMockMvc.perform(get("/api/loan-types/{id}", loanType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loanType.getId().intValue()))
            .andExpect(jsonPath("$.loanCategory").value(DEFAULT_LOAN_CATEGORY.toString()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE));
    }

    @Test
    @Transactional
    public void getNonExistingLoanType() throws Exception {
        // Get the loanType
        restLoanTypeMockMvc.perform(get("/api/loan-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoanType() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();

        // Update the loanType
        LoanType updatedLoanType = loanTypeRepository.findById(loanType.getId()).get();
        // Disconnect from session so that the updates on updatedLoanType are not directly saved in db
        em.detach(updatedLoanType);
        updatedLoanType
            .loanCategory(UPDATED_LOAN_CATEGORY)
            .interestRate(UPDATED_INTEREST_RATE);

        restLoanTypeMockMvc.perform(put("/api/loan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoanType)))
            .andExpect(status().isOk());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
        LoanType testLoanType = loanTypeList.get(loanTypeList.size() - 1);
        assertThat(testLoanType.getLoanCategory()).isEqualTo(UPDATED_LOAN_CATEGORY);
        assertThat(testLoanType.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLoanType() throws Exception {
        int databaseSizeBeforeUpdate = loanTypeRepository.findAll().size();

        // Create the LoanType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanTypeMockMvc.perform(put("/api/loan-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loanType)))
            .andExpect(status().isBadRequest());

        // Validate the LoanType in the database
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoanType() throws Exception {
        // Initialize the database
        loanTypeRepository.saveAndFlush(loanType);

        int databaseSizeBeforeDelete = loanTypeRepository.findAll().size();

        // Get the loanType
        restLoanTypeMockMvc.perform(delete("/api/loan-types/{id}", loanType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LoanType> loanTypeList = loanTypeRepository.findAll();
        assertThat(loanTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanType.class);
        LoanType loanType1 = new LoanType();
        loanType1.setId(1L);
        LoanType loanType2 = new LoanType();
        loanType2.setId(loanType1.getId());
        assertThat(loanType1).isEqualTo(loanType2);
        loanType2.setId(2L);
        assertThat(loanType1).isNotEqualTo(loanType2);
        loanType1.setId(null);
        assertThat(loanType1).isNotEqualTo(loanType2);
    }
}
