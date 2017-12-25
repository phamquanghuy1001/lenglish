import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Exam } from './exam.model';
import { ExamService } from './exam.service';

@Component({
    selector: 'jhi-exam-detail',
    templateUrl: './exam-detail.component.html'
})
export class ExamDetailComponent implements OnInit, OnDestroy {

    exam: Exam;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private examService: ExamService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExams();
    }

    load(id) {
        this.examService.find(id).subscribe((exam) => {
            this.exam = exam;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExams() {
        this.eventSubscriber = this.eventManager.subscribe(
            'examListModification',
            (response) => this.load(this.exam.id)
        );
    }
}
