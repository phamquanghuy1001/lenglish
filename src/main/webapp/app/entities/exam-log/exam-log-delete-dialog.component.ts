import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ExamLog } from './exam-log.model';
import { ExamLogPopupService } from './exam-log-popup.service';
import { ExamLogService } from './exam-log.service';

@Component({
    selector: 'jhi-exam-log-delete-dialog',
    templateUrl: './exam-log-delete-dialog.component.html'
})
export class ExamLogDeleteDialogComponent {

    examLog: ExamLog;

    constructor(
        private examLogService: ExamLogService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.examLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'examLogListModification',
                content: 'Deleted an examLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-exam-log-delete-popup',
    template: ''
})
export class ExamLogDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private examLogPopupService: ExamLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.examLogPopupService
                .open(ExamLogDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
