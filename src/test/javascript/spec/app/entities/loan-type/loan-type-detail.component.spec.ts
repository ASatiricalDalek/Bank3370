/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { LoanTypeDetailComponent } from 'app/entities/loan-type/loan-type-detail.component';
import { LoanType } from 'app/shared/model/loan-type.model';

describe('Component Tests', () => {
    describe('LoanType Management Detail Component', () => {
        let comp: LoanTypeDetailComponent;
        let fixture: ComponentFixture<LoanTypeDetailComponent>;
        const route = ({ data: of({ loanType: new LoanType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoanTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(LoanTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoanTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.loanType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
