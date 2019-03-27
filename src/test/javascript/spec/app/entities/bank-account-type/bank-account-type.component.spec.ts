/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bank3370TestModule } from '../../../test.module';
import { BankAccountTypeComponent } from 'app/entities/bank-account-type/bank-account-type.component';
import { BankAccountTypeService } from 'app/entities/bank-account-type/bank-account-type.service';
import { BankAccountType } from 'app/shared/model/bank-account-type.model';

describe('Component Tests', () => {
    describe('BankAccountType Management Component', () => {
        let comp: BankAccountTypeComponent;
        let fixture: ComponentFixture<BankAccountTypeComponent>;
        let service: BankAccountTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [BankAccountTypeComponent],
                providers: []
            })
                .overrideTemplate(BankAccountTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BankAccountTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankAccountTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BankAccountType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bankAccountTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
