import { IBankAccount } from 'app/shared/model//bank-account.model';

export interface IBankAccountType {
    id?: number;
    accountType?: string;
    accountInterestRate?: number;
    bankAccount?: IBankAccount;
}

export class BankAccountType implements IBankAccountType {
    constructor(public id?: number, public accountType?: string, public accountInterestRate?: number, public bankAccount?: IBankAccount) {}
}
