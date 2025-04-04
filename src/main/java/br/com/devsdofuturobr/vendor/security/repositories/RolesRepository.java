package br.com.devsdofuturobr.vendor.security.repositories;

import br.com.devsdofuturobr.vendor.security.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Set<Roles> findByDescription(String description);
}
