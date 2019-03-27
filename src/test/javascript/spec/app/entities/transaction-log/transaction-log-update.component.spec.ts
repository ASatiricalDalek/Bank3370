/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { TransactionLogUpdateComponent } from 'app/entities/transaction-log/transaction-log-update.component';
import { TransactionLogService } from 'app/entities/transaction-log/transaction-log.service';
import { TransactionLog } from 'app/shared/model/transaction-log.model';

describe('Component Tests', () => {
    describe('TransactionLog Management Update Component', () => {
        let comp: TransactionLogUpdateComponent;
        let fixture: ComponentFixture<TransactionLogUpdateComponent>;
        let service: TransactionLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [TransactionLogUpdateComponent]
            })
                .overrideTemplate(TransactionLogUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TransactionLogUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionLogService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new TransactionLog(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.transactionLog = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new TransactionLog();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.transactionLog = entity;
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
