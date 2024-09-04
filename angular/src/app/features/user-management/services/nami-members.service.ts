import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NamiFetchDetails } from '../models/nami-fetch-details';
import { NamiMember } from '../models/nami-member';

@Injectable({
  providedIn: 'root',
})
export class NamiMembersService {
  constructor(private readonly http: HttpClient) {}

  public getNamiMembers(): Observable<NamiMember[]> {
    return this.http.get<NamiMember[]>('/api/v1/usermanagement/nami-members');
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
