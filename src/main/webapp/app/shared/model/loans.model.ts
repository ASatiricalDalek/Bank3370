import { Moment } from 'moment';
import { ILoanType } from 'app/shared/model//loan-type.model';
import { IPatron } from 'app/shared/model//patron.model';

export interface ILoans {
    id?: number;
    loanPayment?: number;
    loanBalance?: number;
    startDate?: Moment;
    endDate?: Moment;
    loanType?: ILoanType;
    patron?: IPatron;
}

export class Loans implements ILoans {
    constructor(
        public id?: number,
        public loanPayment?: number,
        public loanBalance?: number,
        public startDate?: Moment,
        public endDate?: Moment,
        public loanType?: ILoanType,
        public patron?: IPatron
    ) {}
}
