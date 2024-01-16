package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.domain.models.NamiMember;
import de.stinner.anmeldetool.domain.ports.spi.NamiMemberRepository;
import de.stinner.anmeldetool.infrastructure.jpa.models.NamiMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Repository
public class NamiMemberRepositoryJpaSpi implements NamiMemberRepository {

    private final NamiMemberJpaRepository namiMemberJpaRepository;

    @Override
    public void saveNamiMembers(List<NamiMember> namiMembers) {
        namiMemberJpaRepository.saveAll(
                namiMembers.stream()
                        .map(NamiMemberEntity::fromNamiMember)
                        .toList()
        );
    }

    @Override
    public List<NamiMember> getNamiMembers() {
        return namiMemberJpaRepository.findAll().stream().map(NamiMemberEntity::toNamiMember).toList();
    }
}
