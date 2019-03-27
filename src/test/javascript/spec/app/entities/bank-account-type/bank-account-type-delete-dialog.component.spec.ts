/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { Bank3370TestModule } from '../../../test.module';
import { BankAccountTypeDeleteDialogComponent } from 'app/entities/bank-account-type/bank-account-type-delete-dialog.component';
import { BankAccountTypeService } from 'app/entities/bank-account-type/bank-account-type.service';

describe('Component Tests', () => {
    describe('BankAccountType Management Delete Component', () => {
        let comp: BankAccountTypeDeleteDialogComponent;
        let fixture: ComponentFixture<BankAccountTypeDeleteDialogComponent>;
        let service: BankAccountTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [BankAccountTypeDeleteDialogComponent]
            })
                .overrideTemplate(BankAccountTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BankAccountTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BankAccountTypeService);
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
