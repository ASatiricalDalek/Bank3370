package com.oustudents.bank3370.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Loans.
 */
@Entity
@Table(name = "loans")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Loans implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "loan_payment", nullable = false)
    private Integer loanPayment;

    @NotNull
    @Column(name = "loan_balance", nullable = false)
    private Integer loanBalance;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToOne    @JoinColumn(unique = true)
    private LoanType loanType;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    private Patron patron;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLoanPayment() {
        return loanPayment;
    }

    public Loans loanPayment(Integer loanPayment) {
        this.loanPayment = loanPayment;
        return this;
    }

    public void setLoanPayment(Integer loanPayment) {
        this.loanPayment = loanPayment;
    }

    public Integer getLoanBalance() {
        return loanBalance;
    }

    public Loans loanBalance(Integer loanBalance) {
        this.loanBalance = loanBalance;
        return this;
    }

    public void setLoanBalance(Integer loanBalance) {
        this.loanBalance = loanBalance;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Loans startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Loans endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LoanType getLoanType() {
        return loanType;
    }

    public Loans loanType(LoanType loanType) {
        this.loanType = loanType;
        return this;
    }

    public void setLoanType(LoanType loanType) {
        this.loanType = loanType;
    }

    public Patron getPatron() {
        return patron;
    }

    public Loans patron(Patron patron) {
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
        Loans loans = (Loans) o;
        if (loans.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loans.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Loans{" +
            "id=" + getId() +
            ", loanPayment=" + getLoanPayment() +
            ", loanBalance=" + getLoanBalance() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
