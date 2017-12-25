/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LenglishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConfigDetailComponent } from '../../../../../../main/webapp/app/entities/config/config-detail.component';
import { ConfigService } from '../../../../../../main/webapp/app/entities/config/config.service';
import { Config } from '../../../../../../main/webapp/app/entities/config/config.model';

describe('Component Tests', () => {

    describe('Config Management Detail Component', () => {
        let comp: ConfigDetailComponent;
        let fixture: ComponentFixture<ConfigDetailComponent>;
        let service: ConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LenglishTestModule],
                declarations: [ConfigDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConfigService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Config(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.config).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
