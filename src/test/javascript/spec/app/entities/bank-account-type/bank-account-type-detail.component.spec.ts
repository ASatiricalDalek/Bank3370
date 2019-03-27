/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { BankAccountTypeDetailComponent } from 'app/entities/bank-account-type/bank-account-type-detail.component';
import { BankAccountType } from 'app/shared/model/bank-account-type.model';

describe('Component Tests', () => {
    describe('BankAccountType Management Detail Component', () => {
        let comp: BankAccountTypeDetailComponent;
        let fixture: ComponentFixture<BankAccountTypeDetailComponent>;
        const route = ({ data: of({ bankAccountType: new BankAccountType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [BankAccountTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BankAccountTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BankAccountTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bankAccountType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
