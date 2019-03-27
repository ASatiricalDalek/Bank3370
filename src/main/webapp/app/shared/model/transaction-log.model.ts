import { Moment } from 'moment';
import { IBankAccount } from 'app/shared/model//bank-account.model';

export interface ITransactionLog {
    id?: number;
    transaction?: string;
    amount?: number;
    transDate?: Moment;
    bankAccount?: IBankAccount;
}

export class TransactionLog implements ITransactionLog {
    constructor(
        public id?: number,
        public transaction?: string,
        public amount?: number,
        public transDate?: Moment,
        public bankAccount?: IBankAccount
    ) {}
}
