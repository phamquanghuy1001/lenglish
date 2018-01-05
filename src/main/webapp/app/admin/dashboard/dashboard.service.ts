import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ResponseWrapper, createRequestOption } from '../../shared';
import { Dashboard } from './dashboard.model';

@Injectable()
export class DashboardService {

    private resourceUrl = SERVER_API_URL + 'api/dashboard';

    constructor(private http: Http) { }

    getData(): Observable<Dashboard> {
        return this.http.get(this.resourceUrl).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    /**
     * Convert a returned JSON object to Answer.
     */
    private convertItemFromServer(json: any): Dashboard {
        const entity: Dashboard = Object.assign(new Dashboard(), json);
        return entity;
    }

}
