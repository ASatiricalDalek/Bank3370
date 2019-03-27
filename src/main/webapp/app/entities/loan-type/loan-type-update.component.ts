import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILoanType } from 'app/shared/model/loan-type.model';
import { LoanTypeService } from './loan-type.service';
import { ILoans } from 'app/shared/model/loans.model';
import { LoansService } from 'app/entities/loans';

@Component({
    selector: 'jhi-loan-type-update',
    templateUrl: './loan-type-update.component.html'
})
export class LoanTypeUpdateComponent implements OnInit {
    loanType: ILoanType;
    isSaving: boolean;

    loans: ILoans[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected loanTypeService: LoanTypeService,
        protected loansService: LoansService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ loanType }) => {
            this.loanType = loanType;
        });
        this.loansService.query().subscribe(
            (res: HttpResponse<ILoans[]>) => {
                this.loans = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.loanType.id !== undefined) {
            this.subscribeToSaveResponse(this.loanTypeService.update(this.loanType));
        } else {
            this.subscribeToSaveResponse(this.loanTypeService.create(this.loanType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanType>>) {
        result.subscribe((res: HttpResponse<ILoanType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackLoansById(index: number, item: ILoans) {
        return item.id;
    }
}
