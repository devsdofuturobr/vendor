package br.com.devsdofuturobr.vendor.security.repositories;

import br.com.devsdofuturobr.vendor.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM User u JOIN u.roles p WHERE p.description = 'ADMIN') THEN true ELSE false END")
    boolean adminExists();

//    boolean findFirstByRolesDescription(String description);

    boolean existsByRolesDescription(String name);
}
