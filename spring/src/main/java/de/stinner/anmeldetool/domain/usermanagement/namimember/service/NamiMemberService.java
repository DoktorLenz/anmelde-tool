package de.stinner.anmeldetool.domain.usermanagement.namimember.service;

import de.stinner.anmeldetool.domain.usermanagement.namimember.persistence.NamiMemberEntity;
import de.stinner.anmeldetool.domain.usermanagement.namimember.persistence.NamiMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NamiMemberService {

    private final NamiMemberRepository namiMemberRepository;

    @Transactional
    public void importNamiMembers(Collection<NamiMemberEntity> namiMembers) {
        namiMemberRepository.saveAll(namiMembers);
    }

    @Transactional(readOnly = true)
    public List<NamiMemberEntity> getAllNamiMembers() {
        return namiMemberRepository.findAll();
    }
}
