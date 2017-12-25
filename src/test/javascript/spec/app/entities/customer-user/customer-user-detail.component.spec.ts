/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LenglishTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerUserDetailComponent } from '../../../../../../main/webapp/app/entities/customer-user/customer-user-detail.component';
import { CustomerUserService } from '../../../../../../main/webapp/app/entities/customer-user/customer-user.service';
import { CustomerUser } from '../../../../../../main/webapp/app/entities/customer-user/customer-user.model';

describe('Component Tests', () => {

    describe('CustomerUser Management Detail Component', () => {
        let comp: CustomerUserDetailComponent;
        let fixture: ComponentFixture<CustomerUserDetailComponent>;
        let service: CustomerUserService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LenglishTestModule],
                declarations: [CustomerUserDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerUserService,
                    JhiEventManager
                ]
            }).overrideTemplate(CustomerUserDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerUserDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerUserService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerUser(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerUser).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
