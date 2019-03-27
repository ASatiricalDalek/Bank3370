/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Bank3370TestModule } from '../../../test.module';
import { TransactionLogComponent } from 'app/entities/transaction-log/transaction-log.component';
import { TransactionLogService } from 'app/entities/transaction-log/transaction-log.service';
import { TransactionLog } from 'app/shared/model/transaction-log.model';

describe('Component Tests', () => {
    describe('TransactionLog Management Component', () => {
        let comp: TransactionLogComponent;
        let fixture: ComponentFixture<TransactionLogComponent>;
        let service: TransactionLogService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [TransactionLogComponent],
                providers: []
            })
                .overrideTemplate(TransactionLogComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TransactionLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionLogService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TransactionLog(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.transactionLogs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
