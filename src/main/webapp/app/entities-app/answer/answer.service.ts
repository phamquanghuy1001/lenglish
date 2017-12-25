import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Answer } from './answer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AnswerService {

    private resourceUrl = SERVER_API_URL + 'api/answers';
    private resourceUrlFinder = SERVER_API_URL + 'api/answers_by_question';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

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
}
