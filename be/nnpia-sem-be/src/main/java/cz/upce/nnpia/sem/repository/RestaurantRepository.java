package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {

    List<Restaurant> findAllByDeletedAtIsNull();

    Restaurant getByIdAndDeletedAtIsNull(int id);

    List<Restaurant> findAllByDeletedAtIsNullAndAdmin_Email(String email);

    @Query("SELECT r FROM Restaurant r where r.name LIKE :filterParam OR " +
            "r.address LIKE :filterParam")
    List<Restaurant> filter(@Param("filterParam") String filterParam);
}
