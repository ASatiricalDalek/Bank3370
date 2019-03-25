package com.oustudents.bank3370.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.oustudents.bank3370.domain.BankAccountType;
import com.oustudents.bank3370.repository.BankAccountTypeRepository;
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
 * REST controller for managing BankAccountType.
 */
@RestController
@RequestMapping("/api")
public class BankAccountTypeResource {

    private final Logger log = LoggerFactory.getLogger(BankAccountTypeResource.class);

    private static final String ENTITY_NAME = "bankAccountType";

    private final BankAccountTypeRepository bankAccountTypeRepository;

    public BankAccountTypeResource(BankAccountTypeRepository bankAccountTypeRepository) {
        this.bankAccountTypeRepository = bankAccountTypeRepository;
    }

    /**
     * POST  /bank-account-types : Create a new bankAccountType.
     *
     * @param bankAccountType the bankAccountType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bankAccountType, or with status 400 (Bad Request) if the bankAccountType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bank-account-types")
    @Timed
    public ResponseEntity<BankAccountType> createBankAccountType(@Valid @RequestBody BankAccountType bankAccountType) throws URISyntaxException {
        log.debug("REST request to save BankAccountType : {}", bankAccountType);
        if (bankAccountType.getId() != null) {
            throw new BadRequestAlertException("A new bankAccountType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankAccountType result = bankAccountTypeRepository.save(bankAccountType);
        return ResponseEntity.created(new URI("/api/bank-account-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bank-account-types : Updates an existing bankAccountType.
     *
     * @param bankAccountType the bankAccountType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bankAccountType,
     * or with status 400 (Bad Request) if the bankAccountType is not valid,
     * or with status 500 (Internal Server Error) if the bankAccountType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bank-account-types")
    @Timed
    public ResponseEntity<BankAccountType> updateBankAccountType(@Valid @RequestBody BankAccountType bankAccountType) throws URISyntaxException {
        log.debug("REST request to update BankAccountType : {}", bankAccountType);
        if (bankAccountType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BankAccountType result = bankAccountTypeRepository.save(bankAccountType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bankAccountType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bank-account-types : get all the bankAccountTypes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of bankAccountTypes in body
     */
    @GetMapping("/bank-account-types")
    @Timed
    public List<BankAccountType> getAllBankAccountTypes(@RequestParam(required = false) String filter) {
        if ("bankaccount-is-null".equals(filter)) {
            log.debug("REST request to get all BankAccountTypes where bankAccount is null");
            return StreamSupport
                .stream(bankAccountTypeRepository.findAll().spliterator(), false)
                .filter(bankAccountType -> bankAccountType.getBankAccount() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all BankAccountTypes");
        return bankAccountTypeRepository.findAll();
    }

    /**
     * GET  /bank-account-types/:id : get the "id" bankAccountType.
     *
     * @param id the id of the bankAccountType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bankAccountType, or with status 404 (Not Found)
     */
    @GetMapping("/bank-account-types/{id}")
    @Timed
    public ResponseEntity<BankAccountType> getBankAccountType(@PathVariable Long id) {
        log.debug("REST request to get BankAccountType : {}", id);
        Optional<BankAccountType> bankAccountType = bankAccountTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bankAccountType);
    }

    /**
     * DELETE  /bank-account-types/:id : delete the "id" bankAccountType.
     *
     * @param id the id of the bankAccountType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bank-account-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteBankAccountType(@PathVariable Long id) {
        log.debug("REST request to delete BankAccountType : {}", id);

        bankAccountTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
