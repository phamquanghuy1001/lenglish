/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LenglishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExamLogDetailComponent } from '../../../../../../main/webapp/app/entities/exam-log/exam-log-detail.component';
import { ExamLogService } from '../../../../../../main/webapp/app/entities/exam-log/exam-log.service';
import { ExamLog } from '../../../../../../main/webapp/app/entities/exam-log/exam-log.model';

describe('Component Tests', () => {

    describe('ExamLog Management Detail Component', () => {
        let comp: ExamLogDetailComponent;
        let fixture: ComponentFixture<ExamLogDetailComponent>;
        let service: ExamLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LenglishTestModule],
                declarations: [ExamLogDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExamLogService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExamLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ExamLog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.examLog).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
