package de.stinner.anmeldetool.testdata;

import de.stinner.anmeldetool.domain.nami.service.client.models.NamiMember;
import de.stinner.anmeldetool.domain.nami.service.client.models.NamiMembersWrapper;
import de.stinner.anmeldetool.domain.nami.service.client.models.ResponseType;
import de.stinner.anmeldetool.domain.shared.model.Gender;
import de.stinner.anmeldetool.domain.shared.model.Rank;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NamiTestData {
    public static NamiMembersWrapper namiMembersWrapperSuccessTestData = namiMembersWrapperSuccessTestData();

    private static NamiMembersWrapper namiMembersWrapperSuccessTestData() {
        NamiMembersWrapper namiMembersWrapper = new NamiMembersWrapper();

        NamiMember memberA = new NamiMember();
        memberA.setMemberId(12345);
        memberA.setFirstname("Max");
        memberA.setLastname("Musterman");
        memberA.setDateOfBirth(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        memberA.setRank(Rank.JUNGPFADFINDER);
        memberA.setGender(Gender.MALE);

        List<NamiMember> namiMembers = List.of(
                memberA
        );

        namiMembersWrapper.setSuccess(true);
        namiMembersWrapper.setResponseType(ResponseType.OK);
        namiMembersWrapper.setData(namiMembers);
        namiMembersWrapper.setTotalEntries(namiMembers.size());

        return namiMembersWrapper;
    }
}
