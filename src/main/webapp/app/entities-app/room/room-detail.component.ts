import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Room } from './room.model';
import { RoomService } from './room.service';
import { JhiTrackerService } from './../../shared/tracker/tracker.service';

@Component({
    selector: 'jhi-room-detail',
    templateUrl: './room-detail.component.html',
    styleUrls: [
        'room-detail.component.scss'
    ]
})
export class RoomDetailComponent implements OnInit, OnDestroy {

    room: Room;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private roomService: RoomService,
        private route: ActivatedRoute,
        private trackerService: JhiTrackerService
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRooms();
        this.trackerService.subscribeMessage();
    }

    load(id) {
        this.roomService.find(id).subscribe((room) => {
            this.room = room;
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
        this.trackerService.sendMessage();
    }
}
