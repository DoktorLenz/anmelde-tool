package de.stinner.anmeldetool.hexagonal.domain.service;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;
import de.stinner.anmeldetool.hexagonal.domain.ports.api.NamiMemberService;
import de.stinner.anmeldetool.hexagonal.domain.ports.spi.NamiAdapter;
import de.stinner.anmeldetool.hexagonal.domain.ports.spi.NamiMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NamiMemberServiceImpl implements NamiMemberService {

    private final NamiMemberRepository namiMemberRepository;
    private final NamiAdapter namiAdapter;

    @Override
    public List<NamiMember> getNamiMembers() {
        return namiMemberRepository.getNamiMembers();
    }

    @Transactional
    @Override
    public void triggerImport(String username, String password, String groupingId) {
        List<NamiMember> namiMembers = namiAdapter.getAllMembersOfGrouping(username, password, groupingId);
        namiMemberRepository.saveNamiMembers(namiMembers);
    }
}
