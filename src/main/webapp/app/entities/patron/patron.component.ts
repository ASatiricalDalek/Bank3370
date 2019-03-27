import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPatron } from 'app/shared/model/patron.model';
import { AccountService } from 'app/core';
import { PatronService } from './patron.service';

@Component({
    selector: 'jhi-patron',
    templateUrl: './patron.component.html'
})
export class PatronComponent implements OnInit, OnDestroy {
    patrons: IPatron[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected patronService: PatronService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.patronService.query().subscribe(
            (res: HttpResponse<IPatron[]>) => {
                this.patrons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPatrons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPatron) {
        return item.id;
    }

    registerChangeInPatrons() {
        this.eventSubscriber = this.eventManager.subscribe('patronListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
