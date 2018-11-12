import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserauth } from 'app/shared/model/MasterLoanUserAuth/userauth.model';

type EntityResponseType = HttpResponse<IUserauth>;
type EntityArrayResponseType = HttpResponse<IUserauth[]>;

@Injectable({ providedIn: 'root' })
export class UserauthService {
  public resourceUrl = SERVER_API_URL + 'api/userauths';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/userauths';

  constructor(private http: HttpClient) {}

  create(userauth: IUserauth): Observable<EntityResponseType> {
    return this.http.post<IUserauth>(this.resourceUrl, userauth, { observe: 'response' });
  }

  update(userauth: IUserauth): Observable<EntityResponseType> {
    return this.http.put<IUserauth>(this.resourceUrl, userauth, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserauth>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserauth[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserauth[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
