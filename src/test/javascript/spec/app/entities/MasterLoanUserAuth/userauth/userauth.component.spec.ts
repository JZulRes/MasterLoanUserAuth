/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MasterLoanUserAuthTestModule } from '../../../../test.module';
import { UserauthComponent } from 'app/entities/MasterLoanUserAuth/userauth/userauth.component';
import { UserauthService } from 'app/entities/MasterLoanUserAuth/userauth/userauth.service';
import { Userauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';

describe('Component Tests', () => {
  describe('Userauth Management Component', () => {
    let comp: UserauthComponent;
    let fixture: ComponentFixture<UserauthComponent>;
    let service: UserauthService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanUserAuthTestModule],
        declarations: [UserauthComponent],
        providers: []
      })
        .overrideTemplate(UserauthComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserauthComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserauthService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Userauth(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userauths[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
