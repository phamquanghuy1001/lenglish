import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LessonLog } from './lesson-log.model';
import { LessonLogPopupService } from './lesson-log-popup.service';
import { LessonLogService } from './lesson-log.service';
import { User, UserService } from '../../shared';
import { Lesson, LessonService } from '../lesson';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-lesson-log-dialog',
    templateUrl: './lesson-log-dialog.component.html'
})
export class LessonLogDialogComponent implements OnInit {

    lessonLog: LessonLog;
    isSaving: boolean;

    users: User[];

    lessons: Lesson[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private lessonLogService: LessonLogService,
        private userService: UserService,
        private lessonService: LessonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.lessonService.query()
            .subscribe((res: ResponseWrapper) => { this.lessons = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lessonLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.lessonLogService.update(this.lessonLog));
        } else {
            this.subscribeToSaveResponse(
                this.lessonLogService.create(this.lessonLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<LessonLog>) {
        result.subscribe((res: LessonLog) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: LessonLog) {
        this.eventManager.broadcast({ name: 'lessonLogListModification', content: 'OK'});
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

    trackLessonById(index: number, item: Lesson) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-lesson-log-popup',
    template: ''
})
export class LessonLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lessonLogPopupService: LessonLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.lessonLogPopupService
                    .open(LessonLogDialogComponent as Component, params['id']);
            } else {
                this.lessonLogPopupService
                    .open(LessonLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
