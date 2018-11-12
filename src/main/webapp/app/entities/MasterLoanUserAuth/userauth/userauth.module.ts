import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MasterLoanUserAuthSharedModule } from 'app/shared';
import {
  UserauthComponent,
  UserauthDetailComponent,
  UserauthUpdateComponent,
  UserauthDeletePopupComponent,
  UserauthDeleteDialogComponent,
  userauthRoute,
  userauthPopupRoute
} from './';

const ENTITY_STATES = [...userauthRoute, ...userauthPopupRoute];

@NgModule({
  imports: [MasterLoanUserAuthSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UserauthComponent,
    UserauthDetailComponent,
    UserauthUpdateComponent,
    UserauthDeleteDialogComponent,
    UserauthDeletePopupComponent
  ],
  entryComponents: [UserauthComponent, UserauthUpdateComponent, UserauthDeleteDialogComponent, UserauthDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MasterLoanUserAuthUserauthModule {}
