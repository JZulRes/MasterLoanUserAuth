import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';
import { Principal } from 'app/core';
import { UserauthService } from './userauth.service';

@Component({
  selector: 'jhi-userauth',
  templateUrl: './userauth.component.html'
})
export class UserauthComponent implements OnInit, OnDestroy {
  userauths: IUserauth[];
  currentAccount: any;
  eventSubscriber: Subscription;
  currentSearch: string;

  constructor(
    private userauthService: UserauthService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private principal: Principal
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    if (this.currentSearch) {
      this.userauthService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IUserauth[]>) => (this.userauths = res.body), (res: HttpErrorResponse) => this.onError(res.message));
      return;
    }
    this.userauthService.query().subscribe(
      (res: HttpResponse<IUserauth[]>) => {
        this.userauths = res.body;
        this.currentSearch = '';
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.loadAll();
  }

  clear() {
    this.currentSearch = '';
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserauths();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserauth) {
    return item.id;
  }

  registerChangeInUserauths() {
    this.eventSubscriber = this.eventManager.subscribe('userauthListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
