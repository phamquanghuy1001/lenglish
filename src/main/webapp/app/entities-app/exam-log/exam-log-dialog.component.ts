import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ExamLog } from './exam-log.model';
import { ExamLogPopupService } from './exam-log-popup.service';
import { ExamLogService } from './exam-log.service';
import { User, UserService } from '../../shared';
import { Exam, ExamService } from '../exam';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-exam-log-dialog',
    templateUrl: './exam-log-dialog.component.html'
})
export class ExamLogDialogComponent implements OnInit {

    examLog: ExamLog;
    isSaving: boolean;

    users: User[];

    exams: Exam[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private examLogService: ExamLogService,
        private userService: UserService,
        private examService: ExamService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.examService.query()
            .subscribe((res: ResponseWrapper) => { this.exams = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.examLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.examLogService.update(this.examLog));
        } else {
            this.subscribeToSaveResponse(
                this.examLogService.create(this.examLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<ExamLog>) {
        result.subscribe((res: ExamLog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ExamLog) {
        this.eventManager.broadcast({ name: 'examLogListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackExamById(index: number, item: Exam) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-exam-log-popup',
    template: ''
})
export class ExamLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private examLogPopupService: ExamLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.examLogPopupService
                    .open(ExamLogDialogComponent as Component, params['id']);
            } else {
                this.examLogPopupService
                    .open(ExamLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
