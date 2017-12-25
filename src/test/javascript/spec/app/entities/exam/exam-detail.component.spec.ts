/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LenglishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ExamDetailComponent } from '../../../../../../main/webapp/app/entities/exam/exam-detail.component';
import { ExamService } from '../../../../../../main/webapp/app/entities/exam/exam.service';
import { Exam } from '../../../../../../main/webapp/app/entities/exam/exam.model';

describe('Component Tests', () => {

    describe('Exam Management Detail Component', () => {
        let comp: ExamDetailComponent;
        let fixture: ComponentFixture<ExamDetailComponent>;
        let service: ExamService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LenglishTestModule],
                declarations: [ExamDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ExamService,
                    JhiEventManager
                ]
            }).overrideTemplate(ExamDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ExamDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExamService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Exam(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.exam).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
