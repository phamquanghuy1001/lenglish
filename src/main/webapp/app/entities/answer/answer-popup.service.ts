import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Answer } from './answer.model';
import { AnswerService } from './answer.service';

@Injectable()
export class AnswerPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private answerService: AnswerService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.answerService.find(id).subscribe((answer) => {
                    answer.createDate = this.datePipe
                        .transform(answer.createDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.answerModalRef(component, answer);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.answerModalRef(component, new Answer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    answerModalRef(component: Component, answer: Answer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static' });
        modalRef.componentInstance.answer = answer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }

    // new code
    openForInsert(component: Component, questionId?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (questionId) {
                setTimeout(() => {
                    const answer = new Answer();
                    answer.questionId = questionId;
                    this.ngbModalRef = this.answerModalRef(component, answer);
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

}
