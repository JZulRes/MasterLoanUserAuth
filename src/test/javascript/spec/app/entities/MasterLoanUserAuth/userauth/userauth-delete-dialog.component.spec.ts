/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MasterLoanUserAuthTestModule } from '../../../../test.module';
import { UserauthDeleteDialogComponent } from 'app/entities/MasterLoanUserAuth/userauth/userauth-delete-dialog.component';
import { UserauthService } from 'app/entities/MasterLoanUserAuth/userauth/userauth.service';

describe('Component Tests', () => {
  describe('Userauth Management Delete Component', () => {
    let comp: UserauthDeleteDialogComponent;
    let fixture: ComponentFixture<UserauthDeleteDialogComponent>;
    let service: UserauthService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MasterLoanUserAuthTestModule],
        declarations: [UserauthDeleteDialogComponent]
      })
        .overrideTemplate(UserauthDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserauthDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserauthService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
