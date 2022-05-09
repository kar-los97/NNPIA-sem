package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
}
