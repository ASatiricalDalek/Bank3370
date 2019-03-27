import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionLog } from 'app/shared/model/transaction-log.model';
import { AccountService } from 'app/core';
import { TransactionLogService } from './transaction-log.service';

@Component({
    selector: 'jhi-transaction-log',
    templateUrl: './transaction-log.component.html'
})
export class TransactionLogComponent implements OnInit, OnDestroy {
    transactionLogs: ITransactionLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected transactionLogService: TransactionLogService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.transactionLogService.query().subscribe(
            (res: HttpResponse<ITransactionLog[]>) => {
                this.transactionLogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTransactionLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITransactionLog) {
        return item.id;
    }

    registerChangeInTransactionLogs() {
        this.eventSubscriber = this.eventManager.subscribe('transactionLogListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
