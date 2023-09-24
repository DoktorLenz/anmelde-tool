package de.stinner.anmeldetool.hexagonal.infrastructure.jpa;

import de.stinner.anmeldetool.hexagonal.infrastructure.jpa.models.NamiMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NamiMemberJpaRepository extends JpaRepository<NamiMemberEntity, Long> {
}
