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

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(question: Question): Observable<Question> {
        const copy = this.convert(question);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(question: Question): Observable<Question> {
        const copy = this.convert(question);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Question> {
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
