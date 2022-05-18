package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findAllByDeletedAtIsNull();

    User getUserByIdAndDeletedAtIsNull(int id);

    User getUserByEmailAndPassword(String email, String password);
}
