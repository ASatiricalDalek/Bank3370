import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bank3370SharedModule } from 'app/shared';
import {
    BankAccountTypeComponent,
    BankAccountTypeDetailComponent,
    BankAccountTypeUpdateComponent,
    BankAccountTypeDeletePopupComponent,
    BankAccountTypeDeleteDialogComponent,
    bankAccountTypeRoute,
    bankAccountTypePopupRoute
} from './';

const ENTITY_STATES = [...bankAccountTypeRoute, ...bankAccountTypePopupRoute];

@NgModule({
    imports: [Bank3370SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        BankAccountTypeComponent,
        BankAccountTypeDetailComponent,
        BankAccountTypeUpdateComponent,
        BankAccountTypeDeleteDialogComponent,
        BankAccountTypeDeletePopupComponent
    ],
    entryComponents: [
        BankAccountTypeComponent,
        BankAccountTypeUpdateComponent,
        BankAccountTypeDeleteDialogComponent,
        BankAccountTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Bank3370BankAccountTypeModule {}
