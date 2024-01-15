import { NamiMembersService } from './nami-members.service';
import { MockBuilder, ngMocks } from 'ng-mocks';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NamiMember } from '../model/nami-member';
import { Gender } from 'src/app/lib/models/gender.enum';
import { Rank } from 'src/app/lib/models/rank.enum';
import { NamiFetchDetails } from '../model/nami-fetch-details';

describe('NamiMembersService', () => {

  beforeEach(() =>
    MockBuilder(NamiMembersService).replace(HttpClientModule, HttpClientTestingModule),
  );

  it('should be created', () => {
    const service = ngMocks.findInstance(NamiMembersService);

    expect(service).toBeTruthy();
  });

  it('should read all nami members from api', () => {
    const service = ngMocks.findInstance(NamiMembersService);
    const httpMock = ngMocks.findInstance(HttpTestingController);
    const data: NamiMember[] = [
      {
        firstname: 'Maximilian',
        lastname: 'Musterman',
        gender: Gender.MALE,
        memberId: 0,
        rank: Rank.ROVER,
      },
    ];

    let actual: NamiMember[] = [];
    service.getNamiMembers().subscribe(value => (actual = value));

    const req = httpMock.expectOne('/api/v1/usermanagement/nami-members');

    expect(req.request.method).toEqual('GET');
    req.flush(data);
    httpMock.verify();

    expect(actual).toEqual(data);
  });

  it('should send a new fetch job to the api', () => {
    const service = ngMocks.findInstance(NamiMembersService);
    const httpMock = ngMocks.findInstance(HttpTestingController);

    const namiFetchDetails: NamiFetchDetails = {
      groupId: '1234',
      password: 'testingpassword',
      username: '1234',
    };

    service.fetchNamiMembers(namiFetchDetails).subscribe();

    const req = httpMock.expectOne('/api/v1/usermanagement/trigger-import');

    expect(req.request.method).toEqual('POST');
    expect(req.request.body).toEqual(namiFetchDetails);
  });
});
