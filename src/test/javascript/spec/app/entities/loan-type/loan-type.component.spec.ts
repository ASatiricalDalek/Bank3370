/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bank3370TestModule } from '../../../test.module';
import { LoanTypeComponent } from 'app/entities/loan-type/loan-type.component';
import { LoanTypeService } from 'app/entities/loan-type/loan-type.service';
import { LoanType } from 'app/shared/model/loan-type.model';

describe('Component Tests', () => {
    describe('LoanType Management Component', () => {
        let comp: LoanTypeComponent;
        let fixture: ComponentFixture<LoanTypeComponent>;
        let service: LoanTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoanTypeComponent],
                providers: []
            })
                .overrideTemplate(LoanTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoanTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoanTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new LoanType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.loanTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
