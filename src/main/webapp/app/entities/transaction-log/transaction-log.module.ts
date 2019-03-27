import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Bank3370SharedModule } from 'app/shared';
import {
    TransactionLogComponent,
    TransactionLogDetailComponent,
    TransactionLogUpdateComponent,
    TransactionLogDeletePopupComponent,
    TransactionLogDeleteDialogComponent,
    transactionLogRoute,
    transactionLogPopupRoute
} from './';

const ENTITY_STATES = [...transactionLogRoute, ...transactionLogPopupRoute];

@NgModule({
    imports: [Bank3370SharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TransactionLogComponent,
        TransactionLogDetailComponent,
        TransactionLogUpdateComponent,
        TransactionLogDeleteDialogComponent,
        TransactionLogDeletePopupComponent
    ],
    entryComponents: [
        TransactionLogComponent,
        TransactionLogUpdateComponent,
        TransactionLogDeleteDialogComponent,
        TransactionLogDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Bank3370TransactionLogModule {}
