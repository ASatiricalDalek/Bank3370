import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bank3370SharedModule } from 'app/shared';
import {
    LoanTypeComponent,
    LoanTypeDetailComponent,
    LoanTypeUpdateComponent,
    LoanTypeDeletePopupComponent,
    LoanTypeDeleteDialogComponent,
    loanTypeRoute,
    loanTypePopupRoute
} from './';

const ENTITY_STATES = [...loanTypeRoute, ...loanTypePopupRoute];

@NgModule({
    imports: [Bank3370SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        LoanTypeComponent,
        LoanTypeDetailComponent,
        LoanTypeUpdateComponent,
        LoanTypeDeleteDialogComponent,
        LoanTypeDeletePopupComponent
    ],
    entryComponents: [LoanTypeComponent, LoanTypeUpdateComponent, LoanTypeDeleteDialogComponent, LoanTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Bank3370LoanTypeModule {}
