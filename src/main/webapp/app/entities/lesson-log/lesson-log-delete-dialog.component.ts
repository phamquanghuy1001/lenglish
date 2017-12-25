import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LessonLog } from './lesson-log.model';
import { LessonLogPopupService } from './lesson-log-popup.service';
import { LessonLogService } from './lesson-log.service';

@Component({
    selector: 'jhi-lesson-log-delete-dialog',
    templateUrl: './lesson-log-delete-dialog.component.html'
})
export class LessonLogDeleteDialogComponent {

    lessonLog: LessonLog;

    constructor(
        private lessonLogService: LessonLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lessonLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lessonLogListModification',
                content: 'Deleted an lessonLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lesson-log-delete-popup',
    template: ''
})
export class LessonLogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lessonLogPopupService: LessonLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lessonLogPopupService
                .open(LessonLogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
