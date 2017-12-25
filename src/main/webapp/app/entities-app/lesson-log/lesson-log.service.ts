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
    private resourceUrlByLessson = SERVER_API_URL + 'api/lesson-logs_by_lesson';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

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

    queryByLesson(lessonId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlByLessson + '/' + lessonId, options)
            .map((res: Response) => this.convertResponse(res));
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
