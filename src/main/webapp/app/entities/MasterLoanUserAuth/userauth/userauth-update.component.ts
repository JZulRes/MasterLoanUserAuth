import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IUserauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';
import { UserauthService } from './userauth.service';

@Component({
  selector: 'jhi-userauth-update',
  templateUrl: './userauth-update.component.html'
})
export class UserauthUpdateComponent implements OnInit {
  userauth: IUserauth;
  isSaving: boolean;

  constructor(private userauthService: UserauthService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userauth }) => {
      this.userauth = userauth;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.userauth.id !== undefined) {
      this.subscribeToSaveResponse(this.userauthService.update(this.userauth));
    } else {
      this.subscribeToSaveResponse(this.userauthService.create(this.userauth));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IUserauth>>) {
    result.subscribe((res: HttpResponse<IUserauth>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}
