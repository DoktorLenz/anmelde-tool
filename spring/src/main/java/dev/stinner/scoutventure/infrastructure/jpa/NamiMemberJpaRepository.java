package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.infrastructure.jpa.models.NamiMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NamiMemberJpaRepository extends JpaRepository<NamiMemberEntity, Long> {
}
