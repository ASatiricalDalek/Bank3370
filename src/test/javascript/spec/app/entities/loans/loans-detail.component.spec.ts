/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { LoansDetailComponent } from 'app/entities/loans/loans-detail.component';
import { Loans } from 'app/shared/model/loans.model';

describe('Component Tests', () => {
    describe('Loans Management Detail Component', () => {
        let comp: LoansDetailComponent;
        let fixture: ComponentFixture<LoansDetailComponent>;
        const route = ({ data: of({ loans: new Loans(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoansDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LoansDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoansDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.loans).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
