/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { LoanTypeUpdateComponent } from 'app/entities/loan-type/loan-type-update.component';
import { LoanTypeService } from 'app/entities/loan-type/loan-type.service';
import { LoanType } from 'app/shared/model/loan-type.model';

describe('Component Tests', () => {
    describe('LoanType Management Update Component', () => {
        let comp: LoanTypeUpdateComponent;
        let fixture: ComponentFixture<LoanTypeUpdateComponent>;
        let service: LoanTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoanTypeUpdateComponent]
            })
                .overrideTemplate(LoanTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoanTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoanTypeService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LoanType(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.loanType = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LoanType();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.loanType = entity;
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
