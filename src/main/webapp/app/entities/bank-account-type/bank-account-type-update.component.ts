import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBankAccountType } from 'app/shared/model/bank-account-type.model';
import { BankAccountTypeService } from './bank-account-type.service';
import { IBankAccount } from 'app/shared/model/bank-account.model';
import { BankAccountService } from 'app/entities/bank-account';

@Component({
    selector: 'jhi-bank-account-type-update',
    templateUrl: './bank-account-type-update.component.html'
})
export class BankAccountTypeUpdateComponent implements OnInit {
    bankAccountType: IBankAccountType;
    isSaving: boolean;

    bankaccounts: IBankAccount[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected bankAccountTypeService: BankAccountTypeService,
        protected bankAccountService: BankAccountService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bankAccountType }) => {
            this.bankAccountType = bankAccountType;
        });
        this.bankAccountService.query().subscribe(
            (res: HttpResponse<IBankAccount[]>) => {
                this.bankaccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bankAccountType.id !== undefined) {
            this.subscribeToSaveResponse(this.bankAccountTypeService.update(this.bankAccountType));
        } else {
            this.subscribeToSaveResponse(this.bankAccountTypeService.create(this.bankAccountType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBankAccountType>>) {
        result.subscribe((res: HttpResponse<IBankAccountType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
