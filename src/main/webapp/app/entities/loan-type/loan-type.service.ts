import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILoanType } from 'app/shared/model/loan-type.model';

type EntityResponseType = HttpResponse<ILoanType>;
type EntityArrayResponseType = HttpResponse<ILoanType[]>;

@Injectable({ providedIn: 'root' })
export class LoanTypeService {
    public resourceUrl = SERVER_API_URL + 'api/loan-types';

    constructor(protected http: HttpClient) {}

    create(loanType: ILoanType): Observable<EntityResponseType> {
        return this.http.post<ILoanType>(this.resourceUrl, loanType, { observe: 'response' });
    }

    update(loanType: ILoanType): Observable<EntityResponseType> {
        return this.http.put<ILoanType>(this.resourceUrl, loanType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ILoanType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ILoanType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
