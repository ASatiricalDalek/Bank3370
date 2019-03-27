package com.oustudents.bank3370.repository;

import com.oustudents.bank3370.domain.Loans;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Loans entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {

}
