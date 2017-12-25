import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Room } from './room.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RoomService {

    private resourceUrl = SERVER_API_URL + 'api/rooms';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(room: Room): Observable<Room> {
        const copy = this.convert(room);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(room: Room): Observable<Room> {
        const copy = this.convert(room);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Room> {
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
     * Convert a returned JSON object to Room.
     */
    private convertItemFromServer(json: any): Room {
        const entity: Room = Object.assign(new Room(), json);
        entity.createDate = this.dateUtils
            .convertDateTimeFromServer(json.createDate);
        return entity;
    }

    /**
     * Convert a Room to a JSON which can be sent to the server.
     */
    private convert(room: Room): Room {
        const copy: Room = Object.assign({}, room);

        copy.createDate = this.dateUtils.toDate(room.createDate);
        return copy;
    }
}
