import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { ILoans } from 'app/shared/model/loans.model';
import { LoansService } from './loans.service';
import { ILoanType } from 'app/shared/model/loan-type.model';
import { LoanTypeService } from 'app/entities/loan-type';
import { IPatron } from 'app/shared/model/patron.model';
import { PatronService } from 'app/entities/patron';

@Component({
    selector: 'jhi-loans-update',
    templateUrl: './loans-update.component.html'
})
export class LoansUpdateComponent implements OnInit {
    loans: ILoans;
    isSaving: boolean;

    loantypes: ILoanType[];

    patrons: IPatron[];
    startDateDp: any;
    endDateDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected loansService: LoansService,
        protected loanTypeService: LoanTypeService,
        protected patronService: PatronService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ loans }) => {
            this.loans = loans;
        });
        this.loanTypeService.query({ filter: 'loans-is-null' }).subscribe(
            (res: HttpResponse<ILoanType[]>) => {
                if (!this.loans.loanType || !this.loans.loanType.id) {
                    this.loantypes = res.body;
                } else {
                    this.loanTypeService.find(this.loans.loanType.id).subscribe(
                        (subRes: HttpResponse<ILoanType>) => {
                            this.loantypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
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
        if (this.loans.id !== undefined) {
            this.subscribeToSaveResponse(this.loansService.update(this.loans));
        } else {
            this.subscribeToSaveResponse(this.loansService.create(this.loans));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoans>>) {
        result.subscribe((res: HttpResponse<ILoans>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLoanTypeById(index: number, item: ILoanType) {
        return item.id;
    }

    trackPatronById(index: number, item: IPatron) {
        return item.id;
    }
}
