import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Lesson } from './lesson.model';
import { LessonService } from './lesson.service';

@Component({
    selector: 'jhi-lesson-detail',
    templateUrl: './lesson-detail.component.html',
    styleUrls: [
        'lesson-detail.component.scss'
    ]
})
export class LessonDetailComponent implements OnInit, OnDestroy {

    lesson: Lesson;
    private subscription: Subscription;
    private eventSubscriber: Subscription;
    // chart
    public lineChartData: Array<any> = [
        { data: [2, 4, 3, 5, 4], label: 'Tiến trình' },
    ];
    public lineChartLabels: Array<any> = ['22/5', '22/5', '22/5', '22/5', '22/5'];
    public lineChartOptions: any = {
        responsive: true
    };
    public lineChartColors: Array<any> = [
        { // grey
            backgroundColor: 'rgba(0, 102, 255, 0.2)',
            borderColor: 'rgba(0, 102, 255,1)',
            pointBackgroundColor: 'rgba(148,159,177,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(148,159,177,0.8)'
        }
    ];
    lineChartLegend: Boolean = true;
    lineChartType: String = 'line';

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private lessonService: LessonService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLessons();
    }

    load(id) {
        this.lessonService.find(id).subscribe((lesson) => {
            this.lesson = lesson;
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

    registerChangeInLessons() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lessonListModification',
            (response) => this.load(this.lesson.id)
        );
    }

    // events
    public chartClicked(e: any): void {
        console.log(e);
    }

    public chartHovered(e: any): void {
        console.log(e);
    }
}
