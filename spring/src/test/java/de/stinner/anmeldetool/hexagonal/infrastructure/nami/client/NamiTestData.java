package de.stinner.anmeldetool.hexagonal.infrastructure.nami.client;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NamiTestData {
    public static NamiMembersWrapper namiMembersWrapperSuccessTestData = namiMembersWrapperSuccessTestData();

    private static NamiMembersWrapper namiMembersWrapperSuccessTestData() {
        NamiMembersWrapper namiMembersWrapper = new NamiMembersWrapper();

        ClientNamiMember memberA = new ClientNamiMember();
        memberA.setMemberId(12345L);
        memberA.setFirstname("Max");
        memberA.setLastname("Musterman");
        memberA.setDateOfBirth(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        memberA.setRank(ClientRank.JUNGPFADFINDER);
        memberA.setGender(ClientGender.MALE);

        List<ClientNamiMember> namiMembers = List.of(
                memberA
        );

        namiMembersWrapper.setSuccess(true);
        namiMembersWrapper.setResponseType("OK");
        namiMembersWrapper.setData(namiMembers);
        namiMembersWrapper.setTotalEntries(namiMembers.size());

        return namiMembersWrapper;
    }
}
