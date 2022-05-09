package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
}
