/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LenglishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AnswerDetailComponent } from '../../../../../../main/webapp/app/entities/answer/answer-detail.component';
import { AnswerService } from '../../../../../../main/webapp/app/entities/answer/answer.service';
import { Answer } from '../../../../../../main/webapp/app/entities/answer/answer.model';

describe('Component Tests', () => {

    describe('Answer Management Detail Component', () => {
        let comp: AnswerDetailComponent;
        let fixture: ComponentFixture<AnswerDetailComponent>;
        let service: AnswerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LenglishTestModule],
                declarations: [AnswerDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AnswerService,
                    JhiEventManager
                ]
            }).overrideTemplate(AnswerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnswerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnswerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Answer(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.answer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
