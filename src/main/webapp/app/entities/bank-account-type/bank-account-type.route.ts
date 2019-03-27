import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BankAccountType } from 'app/shared/model/bank-account-type.model';
import { BankAccountTypeService } from './bank-account-type.service';
import { BankAccountTypeComponent } from './bank-account-type.component';
import { BankAccountTypeDetailComponent } from './bank-account-type-detail.component';
import { BankAccountTypeUpdateComponent } from './bank-account-type-update.component';
import { BankAccountTypeDeletePopupComponent } from './bank-account-type-delete-dialog.component';
import { IBankAccountType } from 'app/shared/model/bank-account-type.model';

@Injectable({ providedIn: 'root' })
export class BankAccountTypeResolve implements Resolve<IBankAccountType> {
    constructor(private service: BankAccountTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<BankAccountType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BankAccountType>) => response.ok),
                map((bankAccountType: HttpResponse<BankAccountType>) => bankAccountType.body)
            );
        }
        return of(new BankAccountType());
    }
}

export const bankAccountTypeRoute: Routes = [
    {
        path: 'bank-account-type',
        component: BankAccountTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-account-type/:id/view',
        component: BankAccountTypeDetailComponent,
        resolve: {
            bankAccountType: BankAccountTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-account-type/new',
        component: BankAccountTypeUpdateComponent,
        resolve: {
            bankAccountType: BankAccountTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bank-account-type/:id/edit',
        component: BankAccountTypeUpdateComponent,
        resolve: {
            bankAccountType: BankAccountTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bankAccountTypePopupRoute: Routes = [
    {
        path: 'bank-account-type/:id/delete',
        component: BankAccountTypeDeletePopupComponent,
        resolve: {
            bankAccountType: BankAccountTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BankAccountTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
