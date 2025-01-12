import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NamiFetchDetails } from '../models/nami-fetch-details';
import { NamiMember } from '../models/nami-member';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UserManagementService {
  constructor(private readonly http: HttpClient) {}

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/v1/usermanagement/users');
  }

  public getNamiMembers(): Observable<NamiMember[]> {
    return this.http.get<NamiMember[]>('/api/v1/usermanagement/nami-members');
  }

  public updateNamiMember(namiMember: NamiMember): Observable<void> {
    return this.http.put<void>(
      `/api/v1/usermanagement/nami-members/${namiMember.memberId}`,
      namiMember
    );
  }

  public fetchNamiMembers(
    namiFetchDetails: NamiFetchDetails
  ): Observable<void> {
    return this.http.post<void>(
      '/api/v1/usermanagement/trigger-import',
      namiFetchDetails
    );
  }
}
