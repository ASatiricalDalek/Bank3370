import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoans } from 'app/shared/model/loans.model';

type EntityResponseType = HttpResponse<ILoans>;
type EntityArrayResponseType = HttpResponse<ILoans[]>;

@Injectable({ providedIn: 'root' })
export class LoansService {
    public resourceUrl = SERVER_API_URL + 'api/loans';

    constructor(protected http: HttpClient) {}

    create(loans: ILoans): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(loans);
        return this.http
            .post<ILoans>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(loans: ILoans): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(loans);
        return this.http
            .put<ILoans>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ILoans>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ILoans[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(loans: ILoans): ILoans {
        const copy: ILoans = Object.assign({}, loans, {
            startDate: loans.startDate != null && loans.startDate.isValid() ? loans.startDate.format(DATE_FORMAT) : null,
            endDate: loans.endDate != null && loans.endDate.isValid() ? loans.endDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
            res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((loans: ILoans) => {
                loans.startDate = loans.startDate != null ? moment(loans.startDate) : null;
                loans.endDate = loans.endDate != null ? moment(loans.endDate) : null;
            });
        }
        return res;
    }
}
