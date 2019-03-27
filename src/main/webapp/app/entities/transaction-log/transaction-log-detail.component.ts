import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransactionLog } from 'app/shared/model/transaction-log.model';

@Component({
    selector: 'jhi-transaction-log-detail',
    templateUrl: './transaction-log-detail.component.html'
})
export class TransactionLogDetailComponent implements OnInit {
    transactionLog: ITransactionLog;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ transactionLog }) => {
            this.transactionLog = transactionLog;
        });
    }

    previousState() {
        window.history.back();
    }
}
