import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bank3370SharedModule } from 'app/shared';
import {
    LoansComponent,
    LoansDetailComponent,
    LoansUpdateComponent,
    LoansDeletePopupComponent,
    LoansDeleteDialogComponent,
    loansRoute,
    loansPopupRoute
} from './';

const ENTITY_STATES = [...loansRoute, ...loansPopupRoute];

@NgModule({
    imports: [Bank3370SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [LoansComponent, LoansDetailComponent, LoansUpdateComponent, LoansDeleteDialogComponent, LoansDeletePopupComponent],
    entryComponents: [LoansComponent, LoansUpdateComponent, LoansDeleteDialogComponent, LoansDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Bank3370LoansModule {}
