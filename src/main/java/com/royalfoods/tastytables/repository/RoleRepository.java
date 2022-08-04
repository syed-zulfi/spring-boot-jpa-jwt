package com.royalfoods.tastytables.repository;

import com.royalfoods.tastytables.data.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByRoleNameIn(List<String> roles);
    Optional<Role> findByRoleName(String roleName);
}
