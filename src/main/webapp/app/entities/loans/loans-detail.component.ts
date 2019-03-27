import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILoans } from 'app/shared/model/loans.model';

@Component({
    selector: 'jhi-loans-detail',
    templateUrl: './loans-detail.component.html'
})
export class LoansDetailComponent implements OnInit {
    loans: ILoans;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ loans }) => {
            this.loans = loans;
        });
    }

    previousState() {
        window.history.back();
    }
}
