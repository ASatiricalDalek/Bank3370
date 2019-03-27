import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBankAccountType } from 'app/shared/model/bank-account-type.model';

@Component({
    selector: 'jhi-bank-account-type-detail',
    templateUrl: './bank-account-type-detail.component.html'
})
export class BankAccountTypeDetailComponent implements OnInit {
    bankAccountType: IBankAccountType;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bankAccountType }) => {
            this.bankAccountType = bankAccountType;
        });
    }

    previousState() {
        window.history.back();
    }
}
