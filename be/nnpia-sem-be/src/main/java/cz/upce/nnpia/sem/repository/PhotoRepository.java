package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PhotoRepository extends JpaRepository<Photo,Integer> {
}
