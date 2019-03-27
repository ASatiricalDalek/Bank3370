/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { LoansUpdateComponent } from 'app/entities/loans/loans-update.component';
import { LoansService } from 'app/entities/loans/loans.service';
import { Loans } from 'app/shared/model/loans.model';

describe('Component Tests', () => {
    describe('Loans Management Update Component', () => {
        let comp: LoansUpdateComponent;
        let fixture: ComponentFixture<LoansUpdateComponent>;
        let service: LoansService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoansUpdateComponent]
            })
                .overrideTemplate(LoansUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LoansUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoansService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Loans(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.loans = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Loans();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.loans = entity;
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
