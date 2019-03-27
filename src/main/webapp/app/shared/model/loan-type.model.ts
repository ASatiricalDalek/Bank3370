import { ILoans } from 'app/shared/model//loans.model';

export interface ILoanType {
    id?: number;
    loanCategory?: string;
    interestRate?: number;
    loans?: ILoans;
}

export class LoanType implements ILoanType {
    constructor(public id?: number, public loanCategory?: string, public interestRate?: number, public loans?: ILoans) {}
}
