package dev.stinner.scoutventure.domain.services;

import dev.stinner.scoutventure.domain.models.NamiMember;
import dev.stinner.scoutventure.domain.ports.api.NamiMemberService;
import dev.stinner.scoutventure.domain.ports.spi.NamiAdapter;
import dev.stinner.scoutventure.domain.ports.spi.NamiMemberRepository;
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

    @Override
    public NamiMember getNamiMemberById(Long memberId) {
        return namiMemberRepository.getNamiMemberById(memberId);
    }

    @Transactional
    @Override
    public void triggerImport(String username, String password, String groupingId) {
        List<NamiMember> namiMembers = namiAdapter.getAllMembersOfGrouping(username, password, groupingId);
        namiMemberRepository.saveNamiMembers(namiMembers);
    }

    @Override
    public void updateNamiMember(NamiMember namiMember) {
        namiMemberRepository.updateNamiMember(namiMember);
    }
}
