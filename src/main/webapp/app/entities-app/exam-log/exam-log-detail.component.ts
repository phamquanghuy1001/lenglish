import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ExamLog } from './exam-log.model';
import { ExamLogService } from './exam-log.service';

@Component({
    selector: 'jhi-exam-log-detail',
    templateUrl: './exam-log-detail.component.html'
})
export class ExamLogDetailComponent implements OnInit, OnDestroy {

    examLog: ExamLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private examLogService: ExamLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExamLogs();
    }

    load(id) {
        this.examLogService.find(id).subscribe((examLog) => {
            this.examLog = examLog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExamLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'examLogListModification',
            (response) => this.load(this.examLog.id)
        );
    }
}
