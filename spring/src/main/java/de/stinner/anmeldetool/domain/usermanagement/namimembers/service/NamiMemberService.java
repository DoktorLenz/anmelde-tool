package de.stinner.anmeldetool.domain.usermanagement.namimembers.service;

import de.stinner.anmeldetool.domain.nami.service.models.NamiMember;
import de.stinner.anmeldetool.domain.usermanagement.namimembers.persistence.NamiMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NamiMemberService {

    private final NamiMemberRepository namiMemberRepository;

    @Transactional
    public void importNamiMembers(List<NamiMember> namiMembers) {
        namiMemberRepository.saveAll(namiMembers.stream().map(NamiMember::toNamiMemberEntity).toList());
    }
}
