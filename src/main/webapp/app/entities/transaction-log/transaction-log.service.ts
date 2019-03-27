import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionLog } from 'app/shared/model/transaction-log.model';

type EntityResponseType = HttpResponse<ITransactionLog>;
type EntityArrayResponseType = HttpResponse<ITransactionLog[]>;

@Injectable({ providedIn: 'root' })
export class TransactionLogService {
    public resourceUrl = SERVER_API_URL + 'api/transaction-logs';

    constructor(protected http: HttpClient) {}

    create(transactionLog: ITransactionLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(transactionLog);
        return this.http
            .post<ITransactionLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(transactionLog: ITransactionLog): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(transactionLog);
        return this.http
            .put<ITransactionLog>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITransactionLog>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITransactionLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(transactionLog: ITransactionLog): ITransactionLog {
        const copy: ITransactionLog = Object.assign({}, transactionLog, {
            transDate:
                transactionLog.transDate != null && transactionLog.transDate.isValid() ? transactionLog.transDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.transDate = res.body.transDate != null ? moment(res.body.transDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((transactionLog: ITransactionLog) => {
                transactionLog.transDate = transactionLog.transDate != null ? moment(transactionLog.transDate) : null;
            });
        }
        return res;
    }
}
