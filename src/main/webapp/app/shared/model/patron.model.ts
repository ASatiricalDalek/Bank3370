import { IBankAccount } from 'app/shared/model//bank-account.model';
import { ILoans } from 'app/shared/model//loans.model';

export interface IPatron {
    id?: number;
    patronFirstName?: string;
    patronLastName?: string;
    patronEmail?: string;
    bankAccounts?: IBankAccount[];
    loans?: ILoans[];
}

export class Patron implements IPatron {
    constructor(
        public id?: number,
        public patronFirstName?: string,
        public patronLastName?: string,
        public patronEmail?: string,
        public bankAccounts?: IBankAccount[],
        public loans?: ILoans[]
    ) {}
}
