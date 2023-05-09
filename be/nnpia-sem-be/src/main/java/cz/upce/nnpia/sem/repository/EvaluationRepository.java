package cz.upce.nnpia.sem.repository;

import cz.upce.nnpia.sem.entity.Evaluation;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation,Integer> {

    Evaluation getByIdAndDeletedAtIsNull(int id);

    List<Evaluation> findAllByDeletedAtIsNull();

    List<Evaluation> findAllByRestaurant(Restaurant restaurant);

    List<Evaluation> findAllByUser(User user);

    @Query("SELECT avg(e.stars) FROM Evaluation e where e.restaurant=:restaurant")
    Float getAvgStarsToRestaurant(@Param("restaurant") Restaurant restaurant);

}
