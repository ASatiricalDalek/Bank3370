import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransactionLog } from 'app/shared/model/transaction-log.model';
import { TransactionLogService } from './transaction-log.service';

@Component({
    selector: 'jhi-transaction-log-delete-dialog',
    templateUrl: './transaction-log-delete-dialog.component.html'
})
export class TransactionLogDeleteDialogComponent {
    transactionLog: ITransactionLog;

    constructor(
        protected transactionLogService: TransactionLogService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.transactionLogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'transactionLogListModification',
                content: 'Deleted an transactionLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-transaction-log-delete-popup',
    template: ''
})
export class TransactionLogDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ transactionLog }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TransactionLogDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.transactionLog = transactionLog;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
