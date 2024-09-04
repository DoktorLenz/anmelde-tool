import { Gender } from 'src/app/lib/models/gender.enum';
import { Rank } from 'src/app/lib/models/rank.enum';
import { LegalGuardian } from './legal-guardian';

export interface NamiMember {
  memberId: number;
  firstname: string;
  lastname: string;
  rank: Rank;
  gender: Gender;
  legalGuardians: LegalGuardian[];
}
