import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';
import { UserauthService } from './userauth.service';

@Component({
  selector: 'jhi-userauth-delete-dialog',
  templateUrl: './userauth-delete-dialog.component.html'
})
export class UserauthDeleteDialogComponent {
  userauth: IUserauth;

  constructor(private userauthService: UserauthService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userauthService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'userauthListModification',
        content: 'Deleted an userauth'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-userauth-delete-popup',
  template: ''
})
export class UserauthDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userauth }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UserauthDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.userauth = userauth;
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
