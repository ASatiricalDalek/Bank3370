package com.oustudents.bank3370.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Patron.
 */
@Entity
@Table(name = "patron")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patron implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "patron_first_name", nullable = false)
    private String patronFirstName;

    @NotNull
    @Column(name = "patron_last_name", nullable = false)
    private String patronLastName;

    @Column(name = "patron_email")
    private String patronEmail;

    @OneToMany(mappedBy = "patron")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BankAccount> bankAccounts = new HashSet<>();
    @OneToMany(mappedBy = "patron")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Loans> loans = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatronFirstName() {
        return patronFirstName;
    }

    public Patron patronFirstName(String patronFirstName) {
        this.patronFirstName = patronFirstName;
        return this;
    }

    public void setPatronFirstName(String patronFirstName) {
        this.patronFirstName = patronFirstName;
    }

    public String getPatronLastName() {
        return patronLastName;
    }

    public Patron patronLastName(String patronLastName) {
        this.patronLastName = patronLastName;
        return this;
    }

    public void setPatronLastName(String patronLastName) {
        this.patronLastName = patronLastName;
    }

    public String getPatronEmail() {
        return patronEmail;
    }

    public Patron patronEmail(String patronEmail) {
        this.patronEmail = patronEmail;
        return this;
    }

    public void setPatronEmail(String patronEmail) {
        this.patronEmail = patronEmail;
    }

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public Patron bankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
        return this;
    }

    public Patron addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
        bankAccount.setPatron(this);
        return this;
    }

    public Patron removeBankAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
        bankAccount.setPatron(null);
        return this;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Set<Loans> getLoans() {
        return loans;
    }

    public Patron loans(Set<Loans> loans) {
        this.loans = loans;
        return this;
    }

    public Patron addLoans(Loans loans) {
        this.loans.add(loans);
        loans.setPatron(this);
        return this;
    }

    public Patron removeLoans(Loans loans) {
        this.loans.remove(loans);
        loans.setPatron(null);
        return this;
    }

    public void setLoans(Set<Loans> loans) {
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
        Patron patron = (Patron) o;
        if (patron.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patron.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patron{" +
            "id=" + getId() +
            ", patronFirstName='" + getPatronFirstName() + "'" +
            ", patronLastName='" + getPatronLastName() + "'" +
            ", patronEmail='" + getPatronEmail() + "'" +
            "}";
    }
}
