package pl.wojciechkostecki.revel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechkostecki.revel.model.Role;
import pl.wojciechkostecki.revel.model.UserRole;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(Role name);
    boolean existsByName(Role role);
}
