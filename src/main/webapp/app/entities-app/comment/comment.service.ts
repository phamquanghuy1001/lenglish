import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Comment } from './comment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CommentService {

    private resourceUrl = SERVER_API_URL + 'api/comments';
    private resourceUrlFinder = SERVER_API_URL + 'api/comments_by_post';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(comment: Comment): Observable<Comment> {
        const copy = this.convert(comment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(postId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlFinder + '/' + postId, options)
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
     * Convert a returned JSON object to Comment.
     */
    private convertItemFromServer(json: any): Comment {
        const entity: Comment = Object.assign(new Comment(), json);
        entity.createDate = this.dateUtils
            .convertDateTimeFromServer(json.createDate);
        return entity;
    }

    /**
     * Convert a Comment to a JSON which can be sent to the server.
     */
    private convert(comment: Comment): Comment {
        const copy: Comment = Object.assign({}, comment);

        copy.createDate = this.dateUtils.toDate(comment.createDate);
        return copy;
    }
}
