import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CustomerUser } from './customer-user.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CustomerUserService {

    private resourceUrl = SERVER_API_URL + 'api/customer-users';
    private resourceUrlCurrent = SERVER_API_URL + 'api/current_customer-user';

    constructor(private http: Http) { }

    create(customerUser: CustomerUser): Observable<CustomerUser> {
        const copy = this.convert(customerUser);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(customerUser: CustomerUser): Observable<CustomerUser> {
        const copy = this.convert(customerUser);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CustomerUser> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findByLogin(login: string): Observable<CustomerUser> {
        return this.http.get(`${this.resourceUrl}/full/${login}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findCurrent(): Observable<CustomerUser> {
        return this.http.get(this.resourceUrlCurrent).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CustomerUser.
     */
    private convertItemFromServer(json: any): CustomerUser {
        const entity: CustomerUser = Object.assign(new CustomerUser(), json);
        return entity;
    }

    /**
     * Convert a CustomerUser to a JSON which can be sent to the server.
     */
    private convert(customerUser: CustomerUser): CustomerUser {
        const copy: CustomerUser = Object.assign({}, customerUser);
        return copy;
    }
}
