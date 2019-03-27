import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Loans } from 'app/shared/model/loans.model';
import { LoansService } from './loans.service';
import { LoansComponent } from './loans.component';
import { LoansDetailComponent } from './loans-detail.component';
import { LoansUpdateComponent } from './loans-update.component';
import { LoansDeletePopupComponent } from './loans-delete-dialog.component';
import { ILoans } from 'app/shared/model/loans.model';

@Injectable({ providedIn: 'root' })
export class LoansResolve implements Resolve<ILoans> {
    constructor(private service: LoansService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Loans> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Loans>) => response.ok),
                map((loans: HttpResponse<Loans>) => loans.body)
            );
        }
        return of(new Loans());
    }
}

export const loansRoute: Routes = [
    {
        path: 'loans',
        component: LoansComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'loans/:id/view',
        component: LoansDetailComponent,
        resolve: {
            loans: LoansResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'loans/new',
        component: LoansUpdateComponent,
        resolve: {
            loans: LoansResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'loans/:id/edit',
        component: LoansUpdateComponent,
        resolve: {
            loans: LoansResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const loansPopupRoute: Routes = [
    {
        path: 'loans/:id/delete',
        component: LoansDeletePopupComponent,
        resolve: {
            loans: LoansResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Loans'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
