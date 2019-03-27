import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPatron } from 'app/shared/model/patron.model';
import { PatronService } from './patron.service';

@Component({
    selector: 'jhi-patron-update',
    templateUrl: './patron-update.component.html'
})
export class PatronUpdateComponent implements OnInit {
    patron: IPatron;
    isSaving: boolean;

    constructor(protected patronService: PatronService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ patron }) => {
            this.patron = patron;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.patron.id !== undefined) {
            this.subscribeToSaveResponse(this.patronService.update(this.patron));
        } else {
            this.subscribeToSaveResponse(this.patronService.create(this.patron));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatron>>) {
        result.subscribe((res: HttpResponse<IPatron>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
