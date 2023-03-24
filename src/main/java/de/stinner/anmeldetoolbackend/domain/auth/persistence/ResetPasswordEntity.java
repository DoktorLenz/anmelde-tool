package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "reset_passwords")
public class ResetPasswordEntity {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID resetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private UserDataEntity user;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Instant createdAt;

    private Instant emailSent;

    public static ResetPasswordEntity of(UserDataEntity userDataEntity) {
        ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity();
        resetPasswordEntity.user = userDataEntity;
        return resetPasswordEntity;
    }
}
