/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bank3370TestModule } from '../../../test.module';
import { LoansComponent } from 'app/entities/loans/loans.component';
import { LoansService } from 'app/entities/loans/loans.service';
import { Loans } from 'app/shared/model/loans.model';

describe('Component Tests', () => {
    describe('Loans Management Component', () => {
        let comp: LoansComponent;
        let fixture: ComponentFixture<LoansComponent>;
        let service: LoansService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoansComponent],
                providers: []
            })
                .overrideTemplate(LoansComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoansComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoansService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Loans(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.loans[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
