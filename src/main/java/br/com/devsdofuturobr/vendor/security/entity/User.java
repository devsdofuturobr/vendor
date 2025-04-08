package br.com.devsdofuturobr.vendor.security.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(nullable = false, name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(nullable = false, name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(nullable = false, name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Column(nullable = false)
    private boolean enabled;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

}
