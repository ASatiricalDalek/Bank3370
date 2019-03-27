package com.oustudents.bank3370.repository;

import com.oustudents.bank3370.domain.BankAccountType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BankAccountType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankAccountTypeRepository extends JpaRepository<BankAccountType, Long> {

}
