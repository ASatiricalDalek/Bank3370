/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Bank3370TestModule } from '../../../test.module';
import { LoanTypeDeleteDialogComponent } from 'app/entities/loan-type/loan-type-delete-dialog.component';
import { LoanTypeService } from 'app/entities/loan-type/loan-type.service';

describe('Component Tests', () => {
    describe('LoanType Management Delete Component', () => {
        let comp: LoanTypeDeleteDialogComponent;
        let fixture: ComponentFixture<LoanTypeDeleteDialogComponent>;
        let service: LoanTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [LoanTypeDeleteDialogComponent]
            })
                .overrideTemplate(LoanTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoanTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoanTypeService);
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
