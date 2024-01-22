package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.domain.models.NamiMember;
import dev.stinner.scoutventure.domain.ports.spi.NamiMemberRepository;
import dev.stinner.scoutventure.infrastructure.jpa.models.NamiMemberEntity;
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
