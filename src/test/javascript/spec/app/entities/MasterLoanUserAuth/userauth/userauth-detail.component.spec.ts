/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MasterLoanUserAuthTestModule } from '../../../../test.module';
import { UserauthDetailComponent } from 'app/entities/MasterLoanUserAuth/userauth/userauth-detail.component';
import { Userauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';

describe('Component Tests', () => {
  describe('Userauth Management Detail Component', () => {
    let comp: UserauthDetailComponent;
    let fixture: ComponentFixture<UserauthDetailComponent>;
    const route = ({ data: of({ userauth: new Userauth(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanUserAuthTestModule],
        declarations: [UserauthDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserauthDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserauthDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userauth).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
