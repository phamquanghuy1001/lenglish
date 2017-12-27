import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Room } from './room.model';
import { RoomService } from './room.service';
import { JhiTrackerService } from './../../shared/tracker/tracker.service';
import { Message } from './../../shared/tracker/Message.model';
import {Account, Principal} from '../../shared';

@Component({
    selector: 'jhi-room-detail',
    templateUrl: './room-detail.component.html',
    styleUrls: [
        'room-detail.component.scss'
    ]
})
export class RoomDetailComponent implements OnInit, OnDestroy {
    account: Account;
    room: Room;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    message: string;

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private roomService: RoomService,
        private route: ActivatedRoute,
        public trackerService: JhiTrackerService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRooms();

    }

    load(id) {
        this.roomService.find(id).subscribe((room) => {
            this.room = room;
            const that = this;
            this.trackerService.subscribeMessage(this.room.id, function(data) {

                let message:Message = JSON.parse(data.body);
                that.trackerService.messages.push(message);
                console.log("data", data);
                console.log("body", data.body);
                console.log(typeof (message));
                console.log("account", that.account.login);

                console.log("equal", that.account.login == that.trackerService.messages[0].sender);
            });
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRooms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'roomListModification',
            (response) => this.load(this.room.id)
        );
    }

    sendMessage() {
        this.trackerService.sendMessage( this.message, 1 );
    }
}
