import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Result } from './result.model';
import { Answer } from './answer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AnswerService {

    private resourceUrl = SERVER_API_URL + 'api/answers';
    private resourceUrlFinder = SERVER_API_URL + 'api/answers_by_question';
    private resourceUrlSubmitAnswerByLessson = SERVER_API_URL + 'api/submit_answers/';
    private resourceUrlSubmitAnswerByExam = SERVER_API_URL + 'api/submit_answers_by_exam/';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    submitAnswer(lessonId: number, answers: Answer[]): Observable<Result> {
        return this.http.post(this.resourceUrlSubmitAnswerByLessson + lessonId, answers).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServerResultLesson(jsonResponse);
        });
    }
    submitAnswerByExam(examId: number, answers: Answer[]): Observable<Result> {
        return this.http.post(this.resourceUrlSubmitAnswerByExam + examId, answers).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServerResultLesson(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    queryByQuestion(questionId?: number): Observable<ResponseWrapper> {
        return this.http.get(this.resourceUrlFinder + '/' + questionId)
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
     * Convert a returned JSON object to Answer.
     */
    private convertItemFromServer(json: any): Answer {
        const entity: Answer = Object.assign(new Answer(), json);
        entity.createDate = this.dateUtils
            .convertDateTimeFromServer(json.createDate);
        return entity;
    }

    /**
     * Convert a Answer to a JSON which can be sent to the server.
     */
    private convert(answer: Answer): Answer {
        const copy: Answer = Object.assign({}, answer);

        copy.createDate = this.dateUtils.toDate(answer.createDate);
        return copy;
    }

    /**
    * Convert a returned JSON object to Result Lesson.
    */
    private convertItemFromServerResultLesson(json: any): Result {
        const entity: Result = Object.assign(new Result(), json);
        return entity;
    }

}
