import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Feedback } from './feedback.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FeedbackService {

    private resourceUrl = SERVER_API_URL + 'api/feedbacks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(feedback: Feedback): Observable<Feedback> {
        const copy = this.convert(feedback);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(feedback: Feedback): Observable<Feedback> {
        const copy = this.convert(feedback);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Feedback> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
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
     * Convert a returned JSON object to Feedback.
     */
    private convertItemFromServer(json: any): Feedback {
        const entity: Feedback = Object.assign(new Feedback(), json);
        entity.createDate = this.dateUtils
            .convertDateTimeFromServer(json.createDate);
        return entity;
    }

    /**
     * Convert a Feedback to a JSON which can be sent to the server.
     */
    private convert(feedback: Feedback): Feedback {
        const copy: Feedback = Object.assign({}, feedback);

        copy.createDate = this.dateUtils.toDate(feedback.createDate);
        return copy;
    }
}