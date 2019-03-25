package com.oustudents.bank3370.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oustudents.bank3370.domain.TransactionLog;
import com.oustudents.bank3370.repository.TransactionLogRepository;
import com.oustudents.bank3370.web.rest.errors.BadRequestAlertException;
import com.oustudents.bank3370.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TransactionLog.
 */
@RestController
@RequestMapping("/api")
public class TransactionLogResource {

    private final Logger log = LoggerFactory.getLogger(TransactionLogResource.class);

    private static final String ENTITY_NAME = "transactionLog";

    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogResource(TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }

    /**
     * POST  /transaction-logs : Create a new transactionLog.
     *
     * @param transactionLog the transactionLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new transactionLog, or with status 400 (Bad Request) if the transactionLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/transaction-logs")
    @Timed
    public ResponseEntity<TransactionLog> createTransactionLog(@Valid @RequestBody TransactionLog transactionLog) throws URISyntaxException {
        log.debug("REST request to save TransactionLog : {}", transactionLog);
        if (transactionLog.getId() != null) {
            throw new BadRequestAlertException("A new transactionLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionLog result = transactionLogRepository.save(transactionLog);
        return ResponseEntity.created(new URI("/api/transaction-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /transaction-logs : Updates an existing transactionLog.
     *
     * @param transactionLog the transactionLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated transactionLog,
     * or with status 400 (Bad Request) if the transactionLog is not valid,
     * or with status 500 (Internal Server Error) if the transactionLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/transaction-logs")
    @Timed
    public ResponseEntity<TransactionLog> updateTransactionLog(@Valid @RequestBody TransactionLog transactionLog) throws URISyntaxException {
        log.debug("REST request to update TransactionLog : {}", transactionLog);
        if (transactionLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionLog result = transactionLogRepository.save(transactionLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, transactionLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /transaction-logs : get all the transactionLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of transactionLogs in body
     */
    @GetMapping("/transaction-logs")
    @Timed
    public List<TransactionLog> getAllTransactionLogs() {
        log.debug("REST request to get all TransactionLogs");
        return transactionLogRepository.findAll();
    }

    /**
     * GET  /transaction-logs/:id : get the "id" transactionLog.
     *
     * @param id the id of the transactionLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the transactionLog, or with status 404 (Not Found)
     */
    @GetMapping("/transaction-logs/{id}")
    @Timed
    public ResponseEntity<TransactionLog> getTransactionLog(@PathVariable Long id) {
        log.debug("REST request to get TransactionLog : {}", id);
        Optional<TransactionLog> transactionLog = transactionLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transactionLog);
    }

    /**
     * DELETE  /transaction-logs/:id : delete the "id" transactionLog.
     *
     * @param id the id of the transactionLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/transaction-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTransactionLog(@PathVariable Long id) {
        log.debug("REST request to delete TransactionLog : {}", id);

        transactionLogRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
