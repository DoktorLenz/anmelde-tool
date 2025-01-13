import { Pipe, PipeTransform } from '@angular/core';
import { Rank } from '../../models/rank.enum';

@Pipe({
    name: 'rankColorClass',
    pure: true,
    standalone: false
})
export class RankColorClassPipe implements PipeTransform {
  transform(rank: Rank): string {
    switch (rank) {
      case Rank.WOELFLING:
        return 'bg-woelfling';
      case Rank.JUNGPFADFINDER:
        return 'bg-jungpfadfinder';
      case Rank.PFADFINDER:
        return 'bg-pfadfinder';
      case Rank.ROVER:
        return 'bg-rover';
      default:
        return '';
    }
  }
}
