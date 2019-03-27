package com.oustudents.bank3370.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BankAccountType.
 */
@Entity
@Table(name = "bank_account_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BankAccountType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "account_type", nullable = false)
    private String accountType;

    @NotNull
    @Column(name = "account_interest_rate", nullable = false)
    private Float accountInterestRate;

    @OneToOne(mappedBy = "bankAccountType")
    @JsonIgnore
    private BankAccount bankAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public BankAccountType accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Float getAccountInterestRate() {
        return accountInterestRate;
    }

    public BankAccountType accountInterestRate(Float accountInterestRate) {
        this.accountInterestRate = accountInterestRate;
        return this;
    }

    public void setAccountInterestRate(Float accountInterestRate) {
        this.accountInterestRate = accountInterestRate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public BankAccountType bankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
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
        BankAccountType bankAccountType = (BankAccountType) o;
        if (bankAccountType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bankAccountType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BankAccountType{" +
            "id=" + getId() +
            ", accountType='" + getAccountType() + "'" +
            ", accountInterestRate=" + getAccountInterestRate() +
            "}";
    }
}
