package com.web.respository;

import com.web.domain.Role;
import com.web.model.RoleDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
