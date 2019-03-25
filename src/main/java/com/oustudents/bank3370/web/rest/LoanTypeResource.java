package com.oustudents.bank3370.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oustudents.bank3370.domain.LoanType;
import com.oustudents.bank3370.repository.LoanTypeRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing LoanType.
 */
@RestController
@RequestMapping("/api")
public class LoanTypeResource {

    private final Logger log = LoggerFactory.getLogger(LoanTypeResource.class);

    private static final String ENTITY_NAME = "loanType";

    private final LoanTypeRepository loanTypeRepository;

    public LoanTypeResource(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    /**
     * POST  /loan-types : Create a new loanType.
     *
     * @param loanType the loanType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loanType, or with status 400 (Bad Request) if the loanType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loan-types")
    @Timed
    public ResponseEntity<LoanType> createLoanType(@Valid @RequestBody LoanType loanType) throws URISyntaxException {
        log.debug("REST request to save LoanType : {}", loanType);
        if (loanType.getId() != null) {
            throw new BadRequestAlertException("A new loanType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanType result = loanTypeRepository.save(loanType);
        return ResponseEntity.created(new URI("/api/loan-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loan-types : Updates an existing loanType.
     *
     * @param loanType the loanType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loanType,
     * or with status 400 (Bad Request) if the loanType is not valid,
     * or with status 500 (Internal Server Error) if the loanType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loan-types")
    @Timed
    public ResponseEntity<LoanType> updateLoanType(@Valid @RequestBody LoanType loanType) throws URISyntaxException {
        log.debug("REST request to update LoanType : {}", loanType);
        if (loanType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LoanType result = loanTypeRepository.save(loanType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loanType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loan-types : get all the loanTypes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of loanTypes in body
     */
    @GetMapping("/loan-types")
    @Timed
    public List<LoanType> getAllLoanTypes(@RequestParam(required = false) String filter) {
        if ("loans-is-null".equals(filter)) {
            log.debug("REST request to get all LoanTypes where loans is null");
            return StreamSupport
                .stream(loanTypeRepository.findAll().spliterator(), false)
                .filter(loanType -> loanType.getLoans() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all LoanTypes");
        return loanTypeRepository.findAll();
    }

    /**
     * GET  /loan-types/:id : get the "id" loanType.
     *
     * @param id the id of the loanType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loanType, or with status 404 (Not Found)
     */
    @GetMapping("/loan-types/{id}")
    @Timed
    public ResponseEntity<LoanType> getLoanType(@PathVariable Long id) {
        log.debug("REST request to get LoanType : {}", id);
        Optional<LoanType> loanType = loanTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(loanType);
    }

    /**
     * DELETE  /loan-types/:id : delete the "id" loanType.
     *
     * @param id the id of the loanType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loan-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id) {
        log.debug("REST request to delete LoanType : {}", id);

        loanTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
