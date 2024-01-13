package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.domain.ports.spi.NamiMemberRepository;
import de.stinner.anmeldetool.domain.models.NamiMember;
import de.stinner.anmeldetool.infrastructure.jpa.models.NamiMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NamiMemberRepositoryImpl implements NamiMemberRepository {

    private final NamiMemberJpaRepository namiMemberJpaRepository;

    @Override
    public List<NamiMember> saveNamiMembers(List<NamiMember> namiMembers) {
        return namiMemberJpaRepository.saveAll(
                namiMembers.stream()
                        .map(NamiMemberEntity::fromNamiMember)
                        .toList()
        ).stream().map(NamiMemberEntity::toNamiMember).toList();
    }

    @Override
    public List<NamiMember> getNamiMembers() {
        return namiMemberJpaRepository.findAll().stream().map(NamiMemberEntity::toNamiMember).toList();
    }
}
