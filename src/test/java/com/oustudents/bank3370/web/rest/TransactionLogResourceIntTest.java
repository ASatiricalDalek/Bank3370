package com.oustudents.bank3370.web.rest;

import com.oustudents.bank3370.Bank3370App;

import com.oustudents.bank3370.domain.TransactionLog;
import com.oustudents.bank3370.repository.TransactionLogRepository;
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
 * Test class for the TransactionLogResource REST controller.
 *
 * @see TransactionLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bank3370App.class)
public class TransactionLogResourceIntTest {

    private static final String DEFAULT_TRANSACTION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final LocalDate DEFAULT_TRANS_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANS_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TransactionLogRepository transactionLogRepository;

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

    private MockMvc restTransactionLogMockMvc;

    private TransactionLog transactionLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionLogResource transactionLogResource = new TransactionLogResource(transactionLogRepository);
        this.restTransactionLogMockMvc = MockMvcBuilders.standaloneSetup(transactionLogResource)
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
    public static TransactionLog createEntity(EntityManager em) {
        TransactionLog transactionLog = new TransactionLog()
            .transaction(DEFAULT_TRANSACTION)
            .amount(DEFAULT_AMOUNT)
            .transDate(DEFAULT_TRANS_DATE);
        return transactionLog;
    }

    @Before
    public void initTest() {
        transactionLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionLog() throws Exception {
        int databaseSizeBeforeCreate = transactionLogRepository.findAll().size();

        // Create the TransactionLog
        restTransactionLogMockMvc.perform(post("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionLog)))
            .andExpect(status().isCreated());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionLog testTransactionLog = transactionLogList.get(transactionLogList.size() - 1);
        assertThat(testTransactionLog.getTransaction()).isEqualTo(DEFAULT_TRANSACTION);
        assertThat(testTransactionLog.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTransactionLog.getTransDate()).isEqualTo(DEFAULT_TRANS_DATE);
    }

    @Test
    @Transactional
    public void createTransactionLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionLogRepository.findAll().size();

        // Create the TransactionLog with an existing ID
        transactionLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionLogMockMvc.perform(post("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionLog)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTransactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionLogRepository.findAll().size();
        // set the field null
        transactionLog.setTransaction(null);

        // Create the TransactionLog, which fails.

        restTransactionLogMockMvc.perform(post("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionLog)))
            .andExpect(status().isBadRequest());

        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionLogRepository.findAll().size();
        // set the field null
        transactionLog.setAmount(null);

        // Create the TransactionLog, which fails.

        restTransactionLogMockMvc.perform(post("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionLog)))
            .andExpect(status().isBadRequest());

        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionLogRepository.findAll().size();
        // set the field null
        transactionLog.setTransDate(null);

        // Create the TransactionLog, which fails.

        restTransactionLogMockMvc.perform(post("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionLog)))
            .andExpect(status().isBadRequest());

        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionLogs() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        // Get all the transactionLogList
        restTransactionLogMockMvc.perform(get("/api/transaction-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].transaction").value(hasItem(DEFAULT_TRANSACTION.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].transDate").value(hasItem(DEFAULT_TRANS_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionLog() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        // Get the transactionLog
        restTransactionLogMockMvc.perform(get("/api/transaction-logs/{id}", transactionLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionLog.getId().intValue()))
            .andExpect(jsonPath("$.transaction").value(DEFAULT_TRANSACTION.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.transDate").value(DEFAULT_TRANS_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionLog() throws Exception {
        // Get the transactionLog
        restTransactionLogMockMvc.perform(get("/api/transaction-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionLog() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();

        // Update the transactionLog
        TransactionLog updatedTransactionLog = transactionLogRepository.findById(transactionLog.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionLog are not directly saved in db
        em.detach(updatedTransactionLog);
        updatedTransactionLog
            .transaction(UPDATED_TRANSACTION)
            .amount(UPDATED_AMOUNT)
            .transDate(UPDATED_TRANS_DATE);

        restTransactionLogMockMvc.perform(put("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransactionLog)))
            .andExpect(status().isOk());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
        TransactionLog testTransactionLog = transactionLogList.get(transactionLogList.size() - 1);
        assertThat(testTransactionLog.getTransaction()).isEqualTo(UPDATED_TRANSACTION);
        assertThat(testTransactionLog.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTransactionLog.getTransDate()).isEqualTo(UPDATED_TRANS_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionLog() throws Exception {
        int databaseSizeBeforeUpdate = transactionLogRepository.findAll().size();

        // Create the TransactionLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionLogMockMvc.perform(put("/api/transaction-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionLog)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionLog in the database
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionLog() throws Exception {
        // Initialize the database
        transactionLogRepository.saveAndFlush(transactionLog);

        int databaseSizeBeforeDelete = transactionLogRepository.findAll().size();

        // Get the transactionLog
        restTransactionLogMockMvc.perform(delete("/api/transaction-logs/{id}", transactionLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionLog> transactionLogList = transactionLogRepository.findAll();
        assertThat(transactionLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionLog.class);
        TransactionLog transactionLog1 = new TransactionLog();
        transactionLog1.setId(1L);
        TransactionLog transactionLog2 = new TransactionLog();
        transactionLog2.setId(transactionLog1.getId());
        assertThat(transactionLog1).isEqualTo(transactionLog2);
        transactionLog2.setId(2L);
        assertThat(transactionLog1).isNotEqualTo(transactionLog2);
        transactionLog1.setId(null);
        assertThat(transactionLog1).isNotEqualTo(transactionLog2);
    }
}
