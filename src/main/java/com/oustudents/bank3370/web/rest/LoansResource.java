package com.oustudents.bank3370.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oustudents.bank3370.domain.Loans;
import com.oustudents.bank3370.repository.LoansRepository;
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
 * REST controller for managing Loans.
 */
@RestController
@RequestMapping("/api")
public class LoansResource {

    private final Logger log = LoggerFactory.getLogger(LoansResource.class);

    private static final String ENTITY_NAME = "loans";

    private final LoansRepository loansRepository;

    public LoansResource(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    /**
     * POST  /loans : Create a new loans.
     *
     * @param loans the loans to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loans, or with status 400 (Bad Request) if the loans has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loans")
    @Timed
    public ResponseEntity<Loans> createLoans(@Valid @RequestBody Loans loans) throws URISyntaxException {
        log.debug("REST request to save Loans : {}", loans);
        if (loans.getId() != null) {
            throw new BadRequestAlertException("A new loans cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Loans result = loansRepository.save(loans);
        return ResponseEntity.created(new URI("/api/loans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loans : Updates an existing loans.
     *
     * @param loans the loans to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loans,
     * or with status 400 (Bad Request) if the loans is not valid,
     * or with status 500 (Internal Server Error) if the loans couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loans")
    @Timed
    public ResponseEntity<Loans> updateLoans(@Valid @RequestBody Loans loans) throws URISyntaxException {
        log.debug("REST request to update Loans : {}", loans);
        if (loans.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Loans result = loansRepository.save(loans);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loans.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loans : get all the loans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loans in body
     */
    @GetMapping("/loans")
    @Timed
    public List<Loans> getAllLoans() {
        log.debug("REST request to get all Loans");
        return loansRepository.findAll();
    }

    /**
     * GET  /loans/:id : get the "id" loans.
     *
     * @param id the id of the loans to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loans, or with status 404 (Not Found)
     */
    @GetMapping("/loans/{id}")
    @Timed
    public ResponseEntity<Loans> getLoans(@PathVariable Long id) {
        log.debug("REST request to get Loans : {}", id);
        Optional<Loans> loans = loansRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loans);
    }

    /**
     * DELETE  /loans/:id : delete the "id" loans.
     *
     * @param id the id of the loans to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loans/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoans(@PathVariable Long id) {
        log.debug("REST request to delete Loans : {}", id);

        loansRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
