package com.oustudents.bank3370.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A LoanType.
 */
@Entity
@Table(name = "loan_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoanType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "loan_category", nullable = false)
    private String loanCategory;

    @NotNull
    @Column(name = "interest_rate", nullable = false)
    private Integer interestRate;

    @OneToOne(mappedBy = "loanType")
    @JsonIgnore
    private Loans loans;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public LoanType loanCategory(String loanCategory) {
        this.loanCategory = loanCategory;
        return this;
    }

    public void setLoanCategory(String loanCategory) {
        this.loanCategory = loanCategory;
    }

    public Integer getInterestRate() {
        return interestRate;
    }

    public LoanType interestRate(Integer interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    public void setInterestRate(Integer interestRate) {
        this.interestRate = interestRate;
    }

    public Loans getLoans() {
        return loans;
    }

    public LoanType loans(Loans loans) {
        this.loans = loans;
        return this;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
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
        LoanType loanType = (LoanType) o;
        if (loanType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loanType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoanType{" +
            "id=" + getId() +
            ", loanCategory='" + getLoanCategory() + "'" +
            ", interestRate=" + getInterestRate() +
            "}";
    }
}
