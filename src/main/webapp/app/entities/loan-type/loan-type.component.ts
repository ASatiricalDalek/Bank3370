import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILoanType } from 'app/shared/model/loan-type.model';
import { AccountService } from 'app/core';
import { LoanTypeService } from './loan-type.service';

@Component({
    selector: 'jhi-loan-type',
    templateUrl: './loan-type.component.html'
})
export class LoanTypeComponent implements OnInit, OnDestroy {
    loanTypes: ILoanType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected loanTypeService: LoanTypeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.loanTypeService.query().subscribe(
            (res: HttpResponse<ILoanType[]>) => {
                this.loanTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLoanTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILoanType) {
        return item.id;
    }

    registerChangeInLoanTypes() {
        this.eventSubscriber = this.eventManager.subscribe('loanTypeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
