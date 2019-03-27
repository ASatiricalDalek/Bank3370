package com.oustudents.bank3370.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TransactionLog.
 */
@Entity
@Table(name = "transaction_log")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_transaction", nullable = false)
    private String transaction;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @Column(name = "trans_date", nullable = false)
    private LocalDate transDate;

    @OneToOne    @JoinColumn(unique = true)
    private BankAccount bankAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public TransactionLog transaction(String transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public Integer getAmount() {
        return amount;
    }

    public TransactionLog amount(Integer amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDate getTransDate() {
        return transDate;
    }

    public TransactionLog transDate(LocalDate transDate) {
        this.transDate = transDate;
        return this;
    }

    public void setTransDate(LocalDate transDate) {
        this.transDate = transDate;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public TransactionLog bankAccount(BankAccount bankAccount) {
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
        TransactionLog transactionLog = (TransactionLog) o;
        if (transactionLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionLog{" +
            "id=" + getId() +
            ", transaction='" + getTransaction() + "'" +
            ", amount=" + getAmount() +
            ", transDate='" + getTransDate() + "'" +
            "}";
    }
}
