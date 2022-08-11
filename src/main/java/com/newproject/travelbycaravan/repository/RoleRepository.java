package com.newproject.travelbycaravan.repository;

import com.newproject.travelbycaravan.domain.Role;
import com.newproject.travelbycaravan.domain.enumeration.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(UserRole name);
}
