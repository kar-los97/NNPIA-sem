package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
