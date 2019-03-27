import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LoanType } from 'app/shared/model/loan-type.model';
import { LoanTypeService } from './loan-type.service';
import { LoanTypeComponent } from './loan-type.component';
import { LoanTypeDetailComponent } from './loan-type-detail.component';
import { LoanTypeUpdateComponent } from './loan-type-update.component';
import { LoanTypeDeletePopupComponent } from './loan-type-delete-dialog.component';
import { ILoanType } from 'app/shared/model/loan-type.model';

@Injectable({ providedIn: 'root' })
export class LoanTypeResolve implements Resolve<ILoanType> {
    constructor(private service: LoanTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<LoanType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<LoanType>) => response.ok),
                map((loanType: HttpResponse<LoanType>) => loanType.body)
            );
        }
        return of(new LoanType());
    }
}

export const loanTypeRoute: Routes = [
    {
        path: 'loan-type',
        component: LoanTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'loan-type/:id/view',
        component: LoanTypeDetailComponent,
        resolve: {
            loanType: LoanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'loan-type/new',
        component: LoanTypeUpdateComponent,
        resolve: {
            loanType: LoanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'loan-type/:id/edit',
        component: LoanTypeUpdateComponent,
        resolve: {
            loanType: LoanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loanTypePopupRoute: Routes = [
    {
        path: 'loan-type/:id/delete',
        component: LoanTypeDeletePopupComponent,
        resolve: {
            loanType: LoanTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LoanTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
