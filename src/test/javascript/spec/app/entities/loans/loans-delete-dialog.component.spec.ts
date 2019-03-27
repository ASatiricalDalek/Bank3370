/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Bank3370TestModule } from '../../../test.module';
import { LoansDeleteDialogComponent } from 'app/entities/loans/loans-delete-dialog.component';
import { LoansService } from 'app/entities/loans/loans.service';

describe('Component Tests', () => {
    describe('Loans Management Delete Component', () => {
        let comp: LoansDeleteDialogComponent;
        let fixture: ComponentFixture<LoansDeleteDialogComponent>;
        let service: LoansService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoansDeleteDialogComponent]
            })
                .overrideTemplate(LoansDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoansDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoansService);
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
