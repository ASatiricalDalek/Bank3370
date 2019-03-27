package com.oustudents.bank3370.web.rest;

import com.oustudents.bank3370.Bank3370App;

import com.oustudents.bank3370.domain.Loans;
import com.oustudents.bank3370.repository.LoansRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.oustudents.bank3370.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LoansResource REST controller.
 *
 * @see LoansResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bank3370App.class)
public class LoansResourceIntTest {

    private static final Integer DEFAULT_LOAN_PAYMENT = 1;
    private static final Integer UPDATED_LOAN_PAYMENT = 2;

    private static final Integer DEFAULT_LOAN_BALANCE = 1;
    private static final Integer UPDATED_LOAN_BALANCE = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LoansRepository loansRepository;

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

    private MockMvc restLoansMockMvc;

    private Loans loans;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoansResource loansResource = new LoansResource(loansRepository);
        this.restLoansMockMvc = MockMvcBuilders.standaloneSetup(loansResource)
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
    public static Loans createEntity(EntityManager em) {
        Loans loans = new Loans()
            .loanPayment(DEFAULT_LOAN_PAYMENT)
            .loanBalance(DEFAULT_LOAN_BALANCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return loans;
    }

    @Before
    public void initTest() {
        loans = createEntity(em);
    }

    @Test
    @Transactional
    public void createLoans() throws Exception {
        int databaseSizeBeforeCreate = loansRepository.findAll().size();

        // Create the Loans
        restLoansMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isCreated());

        // Validate the Loans in the database
        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeCreate + 1);
        Loans testLoans = loansList.get(loansList.size() - 1);
        assertThat(testLoans.getLoanPayment()).isEqualTo(DEFAULT_LOAN_PAYMENT);
        assertThat(testLoans.getLoanBalance()).isEqualTo(DEFAULT_LOAN_BALANCE);
        assertThat(testLoans.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLoans.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createLoansWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loansRepository.findAll().size();

        // Create the Loans with an existing ID
        loans.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoansMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isBadRequest());

        // Validate the Loans in the database
        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLoanPaymentIsRequired() throws Exception {
        int databaseSizeBeforeTest = loansRepository.findAll().size();
        // set the field null
        loans.setLoanPayment(null);

        // Create the Loans, which fails.

        restLoansMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isBadRequest());

        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoanBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = loansRepository.findAll().size();
        // set the field null
        loans.setLoanBalance(null);

        // Create the Loans, which fails.

        restLoansMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isBadRequest());

        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loansRepository.findAll().size();
        // set the field null
        loans.setStartDate(null);

        // Create the Loans, which fails.

        restLoansMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isBadRequest());

        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = loansRepository.findAll().size();
        // set the field null
        loans.setEndDate(null);

        // Create the Loans, which fails.

        restLoansMockMvc.perform(post("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isBadRequest());

        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLoans() throws Exception {
        // Initialize the database
        loansRepository.saveAndFlush(loans);

        // Get all the loansList
        restLoansMockMvc.perform(get("/api/loans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loans.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanPayment").value(hasItem(DEFAULT_LOAN_PAYMENT)))
            .andExpect(jsonPath("$.[*].loanBalance").value(hasItem(DEFAULT_LOAN_BALANCE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getLoans() throws Exception {
        // Initialize the database
        loansRepository.saveAndFlush(loans);

        // Get the loans
        restLoansMockMvc.perform(get("/api/loans/{id}", loans.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(loans.getId().intValue()))
            .andExpect(jsonPath("$.loanPayment").value(DEFAULT_LOAN_PAYMENT))
            .andExpect(jsonPath("$.loanBalance").value(DEFAULT_LOAN_BALANCE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLoans() throws Exception {
        // Get the loans
        restLoansMockMvc.perform(get("/api/loans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLoans() throws Exception {
        // Initialize the database
        loansRepository.saveAndFlush(loans);

        int databaseSizeBeforeUpdate = loansRepository.findAll().size();

        // Update the loans
        Loans updatedLoans = loansRepository.findById(loans.getId()).get();
        // Disconnect from session so that the updates on updatedLoans are not directly saved in db
        em.detach(updatedLoans);
        updatedLoans
            .loanPayment(UPDATED_LOAN_PAYMENT)
            .loanBalance(UPDATED_LOAN_BALANCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restLoansMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLoans)))
            .andExpect(status().isOk());

        // Validate the Loans in the database
        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeUpdate);
        Loans testLoans = loansList.get(loansList.size() - 1);
        assertThat(testLoans.getLoanPayment()).isEqualTo(UPDATED_LOAN_PAYMENT);
        assertThat(testLoans.getLoanBalance()).isEqualTo(UPDATED_LOAN_BALANCE);
        assertThat(testLoans.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLoans.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingLoans() throws Exception {
        int databaseSizeBeforeUpdate = loansRepository.findAll().size();

        // Create the Loans

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoansMockMvc.perform(put("/api/loans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loans)))
            .andExpect(status().isBadRequest());

        // Validate the Loans in the database
        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLoans() throws Exception {
        // Initialize the database
        loansRepository.saveAndFlush(loans);

        int databaseSizeBeforeDelete = loansRepository.findAll().size();

        // Get the loans
        restLoansMockMvc.perform(delete("/api/loans/{id}", loans.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Loans> loansList = loansRepository.findAll();
        assertThat(loansList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Loans.class);
        Loans loans1 = new Loans();
        loans1.setId(1L);
        Loans loans2 = new Loans();
        loans2.setId(loans1.getId());
        assertThat(loans1).isEqualTo(loans2);
        loans2.setId(2L);
        assertThat(loans1).isNotEqualTo(loans2);
        loans1.setId(null);
        assertThat(loans1).isNotEqualTo(loans2);
    }
}
