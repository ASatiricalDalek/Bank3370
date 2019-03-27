import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoanType } from 'app/shared/model/loan-type.model';

@Component({
    selector: 'jhi-loan-type-detail',
    templateUrl: './loan-type-detail.component.html'
})
export class LoanTypeDetailComponent implements OnInit {
    loanType: ILoanType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ loanType }) => {
            this.loanType = loanType;
        });
    }

    previousState() {
        window.history.back();
    }
}
