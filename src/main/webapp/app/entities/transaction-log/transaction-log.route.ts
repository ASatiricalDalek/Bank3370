import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionLog } from 'app/shared/model/transaction-log.model';
import { TransactionLogService } from './transaction-log.service';
import { TransactionLogComponent } from './transaction-log.component';
import { TransactionLogDetailComponent } from './transaction-log-detail.component';
import { TransactionLogUpdateComponent } from './transaction-log-update.component';
import { TransactionLogDeletePopupComponent } from './transaction-log-delete-dialog.component';
import { ITransactionLog } from 'app/shared/model/transaction-log.model';

@Injectable({ providedIn: 'root' })
export class TransactionLogResolve implements Resolve<ITransactionLog> {
    constructor(private service: TransactionLogService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<TransactionLog> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TransactionLog>) => response.ok),
                map((transactionLog: HttpResponse<TransactionLog>) => transactionLog.body)
            );
        }
        return of(new TransactionLog());
    }
}

export const transactionLogRoute: Routes = [
    {
        path: 'transaction-log',
        component: TransactionLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TransactionLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transaction-log/:id/view',
        component: TransactionLogDetailComponent,
        resolve: {
            transactionLog: TransactionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TransactionLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transaction-log/new',
        component: TransactionLogUpdateComponent,
        resolve: {
            transactionLog: TransactionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TransactionLogs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'transaction-log/:id/edit',
        component: TransactionLogUpdateComponent,
        resolve: {
            transactionLog: TransactionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TransactionLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const transactionLogPopupRoute: Routes = [
    {
        path: 'transaction-log/:id/delete',
        component: TransactionLogDeletePopupComponent,
        resolve: {
            transactionLog: TransactionLogResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TransactionLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
