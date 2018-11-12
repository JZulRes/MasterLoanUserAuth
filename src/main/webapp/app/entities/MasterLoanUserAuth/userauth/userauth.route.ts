import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Userauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';
import { UserauthService } from './userauth.service';
import { UserauthComponent } from './userauth.component';
import { UserauthDetailComponent } from './userauth-detail.component';
import { UserauthUpdateComponent } from './userauth-update.component';
import { UserauthDeletePopupComponent } from './userauth-delete-dialog.component';
import { IUserauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';

@Injectable({ providedIn: 'root' })
export class UserauthResolve implements Resolve<IUserauth> {
  constructor(private service: UserauthService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Userauth> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Userauth>) => response.ok),
        map((userauth: HttpResponse<Userauth>) => userauth.body)
      );
    }
    return of(new Userauth());
  }
}

export const userauthRoute: Routes = [
  {
    path: 'userauth',
    component: UserauthComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userauths'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'userauth/:id/view',
    component: UserauthDetailComponent,
    resolve: {
      userauth: UserauthResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userauths'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'userauth/new',
    component: UserauthUpdateComponent,
    resolve: {
      userauth: UserauthResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userauths'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'userauth/:id/edit',
    component: UserauthUpdateComponent,
    resolve: {
      userauth: UserauthResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userauths'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userauthPopupRoute: Routes = [
  {
    path: 'userauth/:id/delete',
    component: UserauthDeletePopupComponent,
    resolve: {
      userauth: UserauthResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Userauths'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
