import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { LessonLog } from './lesson-log.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LessonLogService {

    private resourceUrl = SERVER_API_URL + 'api/lesson-logs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(lessonLog: LessonLog): Observable<LessonLog> {
        const copy = this.convert(lessonLog);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(lessonLog: LessonLog): Observable<LessonLog> {
        const copy = this.convert(lessonLog);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<LessonLog> {
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
     * Convert a returned JSON object to LessonLog.
     */
    private convertItemFromServer(json: any): LessonLog {
        const entity: LessonLog = Object.assign(new LessonLog(), json);
        entity.createDate = this.dateUtils
            .convertDateTimeFromServer(json.createDate);
        return entity;
    }

    /**
     * Convert a LessonLog to a JSON which can be sent to the server.
     */
    private convert(lessonLog: LessonLog): LessonLog {
        const copy: LessonLog = Object.assign({}, lessonLog);

        copy.createDate = this.dateUtils.toDate(lessonLog.createDate);
        return copy;
    }
}
