import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ITransactionLog } from 'app/shared/model/transaction-log.model';
import { TransactionLogService } from './transaction-log.service';
import { IBankAccount } from 'app/shared/model/bank-account.model';
import { BankAccountService } from 'app/entities/bank-account';

@Component({
    selector: 'jhi-transaction-log-update',
    templateUrl: './transaction-log-update.component.html'
})
export class TransactionLogUpdateComponent implements OnInit {
    transactionLog: ITransactionLog;
    isSaving: boolean;

    bankaccounts: IBankAccount[];
    transDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected transactionLogService: TransactionLogService,
        protected bankAccountService: BankAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ transactionLog }) => {
            this.transactionLog = transactionLog;
        });
        this.bankAccountService.query({ filter: 'transactionlog-is-null' }).subscribe(
            (res: HttpResponse<IBankAccount[]>) => {
                if (!this.transactionLog.bankAccount || !this.transactionLog.bankAccount.id) {
                    this.bankaccounts = res.body;
                } else {
                    this.bankAccountService.find(this.transactionLog.bankAccount.id).subscribe(
                        (subRes: HttpResponse<IBankAccount>) => {
                            this.bankaccounts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.transactionLog.id !== undefined) {
            this.subscribeToSaveResponse(this.transactionLogService.update(this.transactionLog));
        } else {
            this.subscribeToSaveResponse(this.transactionLogService.create(this.transactionLog));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionLog>>) {
        result.subscribe((res: HttpResponse<ITransactionLog>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackBankAccountById(index: number, item: IBankAccount) {
        return item.id;
    }
}
