import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';

@Component({
  selector: 'jhi-userauth-detail',
  templateUrl: './userauth-detail.component.html'
})
export class UserauthDetailComponent implements OnInit {
  userauth: IUserauth;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userauth }) => {
      this.userauth = userauth;
    });
  }

  previousState() {
    window.history.back();
  }
}
