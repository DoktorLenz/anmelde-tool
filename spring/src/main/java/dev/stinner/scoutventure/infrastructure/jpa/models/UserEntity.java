package dev.stinner.scoutventure.infrastructure.jpa.models;

import dev.stinner.scoutventure.domain.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "iam_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    /**
     * The subject in a jwt used for oauth has no specified format.
     * e.g. Zitadel uses a random number, Keycloak uses an uuid, etc.
     */
    @Id
    @Column(nullable = false, updatable = false)
    private String subject;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "iam_users_nami_members_mapping", joinColumns = @JoinColumn(name = "subject"), inverseJoinColumns = @JoinColumn(name = "member_id"))
    private Set<NamiMemberEntity> AssignedNamiMembers;

    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getSubject(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail(),
                Set.of()
        );
    }

    public User toDomain() {
        return new User(this.subject, this.firstname, this.lastname, this.username, this.email);
    }
}
