/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { BankAccountTypeUpdateComponent } from 'app/entities/bank-account-type/bank-account-type-update.component';
import { BankAccountTypeService } from 'app/entities/bank-account-type/bank-account-type.service';
import { BankAccountType } from 'app/shared/model/bank-account-type.model';

describe('Component Tests', () => {
    describe('BankAccountType Management Update Component', () => {
        let comp: BankAccountTypeUpdateComponent;
        let fixture: ComponentFixture<BankAccountTypeUpdateComponent>;
        let service: BankAccountTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [BankAccountTypeUpdateComponent]
            })
                .overrideTemplate(BankAccountTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BankAccountTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankAccountTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new BankAccountType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bankAccountType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new BankAccountType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.bankAccountType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
