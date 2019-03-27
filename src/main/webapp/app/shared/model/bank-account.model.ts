import { IBankAccountType } from 'app/shared/model//bank-account-type.model';
import { ITransactionLog } from 'app/shared/model//transaction-log.model';
import { IPatron } from 'app/shared/model//patron.model';

export interface IBankAccount {
    id?: number;
    accountName?: string;
    accountBalance?: number;
    insurance?: boolean;
    bankAccountType?: IBankAccountType;
    transactionLog?: ITransactionLog;
    patron?: IPatron;
}

export class BankAccount implements IBankAccount {
    constructor(
        public id?: number,
        public accountName?: string,
        public accountBalance?: number,
        public insurance?: boolean,
        public bankAccountType?: IBankAccountType,
        public transactionLog?: ITransactionLog,
        public patron?: IPatron
    ) {
        this.insurance = this.insurance || false;
    }
}
