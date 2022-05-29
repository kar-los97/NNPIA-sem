package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {

    List<Restaurant> findAllByDeletedAtIsNull();

    Restaurant getByIdAndDeletedAtIsNull(int id);

    List<Restaurant> findAllByDeletedAtIsNullAndAdmin_Email(String email);
}
