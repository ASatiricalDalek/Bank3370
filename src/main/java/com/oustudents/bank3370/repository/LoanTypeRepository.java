package com.oustudents.bank3370.repository;

import com.oustudents.bank3370.domain.LoanType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LoanType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

}
