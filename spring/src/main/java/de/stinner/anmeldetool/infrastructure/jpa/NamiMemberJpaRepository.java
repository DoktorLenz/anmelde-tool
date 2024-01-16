package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.infrastructure.jpa.models.NamiMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NamiMemberJpaRepository extends JpaRepository<NamiMemberEntity, Long> {
}
