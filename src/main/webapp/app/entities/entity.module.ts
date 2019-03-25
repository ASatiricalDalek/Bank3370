import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Bank3370PatronModule } from './patron/patron.module';
import { Bank3370BankAccountModule } from './bank-account/bank-account.module';
import { Bank3370LoansModule } from './loans/loans.module';
import { Bank3370LoanTypeModule } from './loan-type/loan-type.module';
import { Bank3370BankAccountTypeModule } from './bank-account-type/bank-account-type.module';
import { Bank3370TransactionLogModule } from './transaction-log/transaction-log.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        Bank3370PatronModule,
        Bank3370BankAccountModule,
        Bank3370LoansModule,
        Bank3370LoanTypeModule,
        Bank3370BankAccountTypeModule,
        Bank3370TransactionLogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Bank3370EntityModule {}
