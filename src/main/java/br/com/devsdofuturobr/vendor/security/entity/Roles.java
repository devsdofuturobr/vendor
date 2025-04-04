package br.com.devsdofuturobr.vendor.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }

    public Roles(String description) {
        this.description = description;
    }
}
