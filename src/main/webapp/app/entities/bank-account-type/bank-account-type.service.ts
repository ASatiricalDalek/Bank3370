import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBankAccountType } from 'app/shared/model/bank-account-type.model';

type EntityResponseType = HttpResponse<IBankAccountType>;
type EntityArrayResponseType = HttpResponse<IBankAccountType[]>;

@Injectable({ providedIn: 'root' })
export class BankAccountTypeService {
    public resourceUrl = SERVER_API_URL + 'api/bank-account-types';

    constructor(protected http: HttpClient) {}

    create(bankAccountType: IBankAccountType): Observable<EntityResponseType> {
        return this.http.post<IBankAccountType>(this.resourceUrl, bankAccountType, { observe: 'response' });
    }

    update(bankAccountType: IBankAccountType): Observable<EntityResponseType> {
        return this.http.put<IBankAccountType>(this.resourceUrl, bankAccountType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBankAccountType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBankAccountType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
