/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LenglishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LessonLogDetailComponent } from '../../../../../../main/webapp/app/entities/lesson-log/lesson-log-detail.component';
import { LessonLogService } from '../../../../../../main/webapp/app/entities/lesson-log/lesson-log.service';
import { LessonLog } from '../../../../../../main/webapp/app/entities/lesson-log/lesson-log.model';

describe('Component Tests', () => {

    describe('LessonLog Management Detail Component', () => {
        let comp: LessonLogDetailComponent;
        let fixture: ComponentFixture<LessonLogDetailComponent>;
        let service: LessonLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LenglishTestModule],
                declarations: [LessonLogDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LessonLogService,
                    JhiEventManager
                ]
            }).overrideTemplate(LessonLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LessonLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LessonLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LessonLog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.lessonLog).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
