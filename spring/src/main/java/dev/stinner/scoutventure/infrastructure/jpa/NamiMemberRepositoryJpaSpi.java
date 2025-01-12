package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.domain.models.NamiMember;
import dev.stinner.scoutventure.domain.ports.spi.NamiMemberRepository;
import dev.stinner.scoutventure.infrastructure.jpa.models.NamiMemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
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

    @Override
    public NamiMember getNamiMemberById(Long memberId) {
        return namiMemberJpaRepository.findByMemberId(memberId).orElseThrow(NoSuchElementException::new).toNamiMember();
    }

    @Override
    public void updateNamiMember(NamiMember namiMember) {
        var x = NamiMemberEntity.fromNamiMember(namiMember);
        var y = namiMemberJpaRepository.save(x);
        log.info("Updated NamiMember: {}", y.toNamiMember());
    }
}
