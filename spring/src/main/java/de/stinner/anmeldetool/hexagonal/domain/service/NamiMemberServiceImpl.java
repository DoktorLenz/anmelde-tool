package de.stinner.anmeldetool.hexagonal.domain.service;

import de.stinner.anmeldetool.hexagonal.domain.models.NamiMember;
import de.stinner.anmeldetool.hexagonal.domain.ports.api.NamiMemberService;
import de.stinner.anmeldetool.hexagonal.domain.ports.spi.NamiMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class NamiMemberServiceImpl implements NamiMemberService {

    private final NamiMemberRepository namiMemberRepository;

    @Override
    public List<NamiMember> getNamiMembers() {
        return namiMemberRepository.getNamiMembers();
    }
}
