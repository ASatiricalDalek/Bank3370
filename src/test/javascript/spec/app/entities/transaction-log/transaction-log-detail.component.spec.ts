/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Bank3370TestModule } from '../../../test.module';
import { TransactionLogDetailComponent } from 'app/entities/transaction-log/transaction-log-detail.component';
import { TransactionLog } from 'app/shared/model/transaction-log.model';

describe('Component Tests', () => {
    describe('TransactionLog Management Detail Component', () => {
        let comp: TransactionLogDetailComponent;
        let fixture: ComponentFixture<TransactionLogDetailComponent>;
        const route = ({ data: of({ transactionLog: new TransactionLog(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Bank3370TestModule],
                declarations: [TransactionLogDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TransactionLogDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TransactionLogDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.transactionLog).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
