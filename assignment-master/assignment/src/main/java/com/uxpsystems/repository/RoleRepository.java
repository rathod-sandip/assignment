package com.uxpsystems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uxpsystems.model.Role;
import com.uxpsystems.model.RoleName;


/**
 * @author Sandip Rathod
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
