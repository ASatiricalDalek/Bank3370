/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Bank3370TestModule } from '../../../test.module';
import { TransactionLogDeleteDialogComponent } from 'app/entities/transaction-log/transaction-log-delete-dialog.component';
import { TransactionLogService } from 'app/entities/transaction-log/transaction-log.service';

describe('Component Tests', () => {
    describe('TransactionLog Management Delete Component', () => {
        let comp: TransactionLogDeleteDialogComponent;
        let fixture: ComponentFixture<TransactionLogDeleteDialogComponent>;
        let service: TransactionLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [TransactionLogDeleteDialogComponent]
            })
                .overrideTemplate(TransactionLogDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TransactionLogDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
