import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILoans } from 'app/shared/model/loans.model';
import { AccountService } from 'app/core';
import { LoansService } from './loans.service';

@Component({
    selector: 'jhi-loans',
    templateUrl: './loans.component.html'
})
export class LoansComponent implements OnInit, OnDestroy {
    loans: ILoans[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected loansService: LoansService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.loansService.query().subscribe(
            (res: HttpResponse<ILoans[]>) => {
                this.loans = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInLoans();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ILoans) {
        return item.id;
    }

    registerChangeInLoans() {
        this.eventSubscriber = this.eventManager.subscribe('loansListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
