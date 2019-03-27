package com.oustudents.bank3370.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BankAccount.
 */
@Entity
@Table(name = "bank_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_name", nullable = false)
    private String accountName;

    @NotNull
    @Column(name = "account_balance", nullable = false)
    private Double accountBalance;

    @NotNull
    @Column(name = "insurance", nullable = false)
    private Boolean insurance;

    @OneToOne    @JoinColumn(unique = true)
    private BankAccountType bankAccountType;

    @OneToOne(mappedBy = "bankAccount")
    @JsonIgnore
    private TransactionLog transactionLog;

    @ManyToOne
    @JsonIgnoreProperties("bankAccounts")
    private Patron patron;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public BankAccount accountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public BankAccount accountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Boolean isInsurance() {
        return insurance;
    }

    public BankAccount insurance(Boolean insurance) {
        this.insurance = insurance;
        return this;
    }

    public void setInsurance(Boolean insurance) {
        this.insurance = insurance;
    }

    public BankAccountType getBankAccountType() {
        return bankAccountType;
    }

    public BankAccount bankAccountType(BankAccountType bankAccountType) {
        this.bankAccountType = bankAccountType;
        return this;
    }

    public void setBankAccountType(BankAccountType bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public TransactionLog getTransactionLog() {
        return transactionLog;
    }

    public BankAccount transactionLog(TransactionLog transactionLog) {
        this.transactionLog = transactionLog;
        return this;
    }

    public void setTransactionLog(TransactionLog transactionLog) {
        this.transactionLog = transactionLog;
    }

    public Patron getPatron() {
        return patron;
    }

    public BankAccount patron(Patron patron) {
        this.patron = patron;
        return this;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankAccount bankAccount = (BankAccount) o;
        if (bankAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", accountBalance=" + getAccountBalance() +
            ", insurance='" + isInsurance() + "'" +
            "}";
    }
}
