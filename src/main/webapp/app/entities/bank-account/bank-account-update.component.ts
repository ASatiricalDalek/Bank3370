import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBankAccount } from 'app/shared/model/bank-account.model';
import { BankAccountService } from './bank-account.service';
import { IBankAccountType } from 'app/shared/model/bank-account-type.model';
import { BankAccountTypeService } from 'app/entities/bank-account-type';
import { ITransactionLog } from 'app/shared/model/transaction-log.model';
import { TransactionLogService } from 'app/entities/transaction-log';
import { IPatron } from 'app/shared/model/patron.model';
import { PatronService } from 'app/entities/patron';

@Component({
    selector: 'jhi-bank-account-update',
    templateUrl: './bank-account-update.component.html'
})
export class BankAccountUpdateComponent implements OnInit {
    bankAccount: IBankAccount;
    isSaving: boolean;

    bankaccounttypes: IBankAccountType[];

    transactionlogs: ITransactionLog[];

    patrons: IPatron[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bankAccountService: BankAccountService,
        protected bankAccountTypeService: BankAccountTypeService,
        protected transactionLogService: TransactionLogService,
        protected patronService: PatronService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bankAccount }) => {
            this.bankAccount = bankAccount;
        });
        this.bankAccountTypeService.query({ filter: 'bankaccount-is-null' }).subscribe(
            (res: HttpResponse<IBankAccountType[]>) => {
                if (!this.bankAccount.bankAccountType || !this.bankAccount.bankAccountType.id) {
                    this.bankaccounttypes = res.body;
                } else {
                    this.bankAccountTypeService.find(this.bankAccount.bankAccountType.id).subscribe(
                        (subRes: HttpResponse<IBankAccountType>) => {
                            this.bankaccounttypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.transactionLogService.query().subscribe(
            (res: HttpResponse<ITransactionLog[]>) => {
                this.transactionlogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.patronService.query().subscribe(
            (res: HttpResponse<IPatron[]>) => {
                this.patrons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bankAccount.id !== undefined) {
            this.subscribeToSaveResponse(this.bankAccountService.update(this.bankAccount));
        } else {
            this.subscribeToSaveResponse(this.bankAccountService.create(this.bankAccount));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankAccount>>) {
        result.subscribe((res: HttpResponse<IBankAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBankAccountTypeById(index: number, item: IBankAccountType) {
        return item.id;
    }

    trackTransactionLogById(index: number, item: ITransactionLog) {
        return item.id;
    }

    trackPatronById(index: number, item: IPatron) {
        return item.id;
    }
}
