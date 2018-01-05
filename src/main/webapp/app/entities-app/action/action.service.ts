import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { Action } from './action.model';

@Injectable()
export class ActionService {

    private resourceUrl = SERVER_API_URL + 'api/action';

    constructor(private http: Http) { }

    getData(): Observable<Action> {
        return this.http.get(this.resourceUrl).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });                                          
    }

    /**
     * Convert a returned JSON object to Answer.
     */
    private convertItemFromServer(json: any): Action {
        const entity: Action = Object.assign(new Action(), json);
        return entity;
    }

}
