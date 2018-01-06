import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Question } from './question.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class QuestionService {

    private resourceUrl = SERVER_API_URL + 'api/questions';
    private resourceUrlFinderByQuestion = SERVER_API_URL + 'api/questions_by_lesson';
    private resourceUrlFinderByExam = SERVER_API_URL + 'api/questions_by_exam';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByLesson(lessonId?: number): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrlFinderByQuestion + '/' + lessonId)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByExam(examId?: number): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrlFinderByExam + '/' + examId)
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
     * Convert a returned JSON object to Question.
     */
    private convertItemFromServer(json: any): Question {
        const entity: Question = Object.assign(new Question(), json);
        entity.createDate = this.dateUtils
            .convertDateTimeFromServer(json.createDate);
        return entity;
    }

    /**
     * Convert a Question to a JSON which can be sent to the server.
     */
    private convert(question: Question): Question {
        const copy: Question = Object.assign({}, question);

        copy.createDate = this.dateUtils.toDate(question.createDate);
        return copy;
    }
}
