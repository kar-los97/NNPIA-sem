package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.Evaluation;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.entity.Role;
import cz.upce.nnpia.sem.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class EvaluationServiceTest {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @BeforeEach
    public void setRestAndUser(){
        User user = new User();
        user.setId(1);
        user.setPassword("Heslo+123");
        user.setEmail("muj@email.cz");
        user.setFirstname("Name");
        user.setLastname("Lastname");
        user.setRole(Role.ROLE_Admin);
        user.setLastUpdate(new Date());
        user = userService.addUser(user);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1);
        restaurant.setAdmin(user);
        restaurant.setAddress("Adresa");
        restaurant.setNote("Poznamka");
        restaurant.setName("Restaurace");

        restaurantService.createRestaurant(restaurant);
    }

    @Test
    public void testCreateEvaluation(){
        Evaluation evaluation = new Evaluation();
        evaluation.setCreatedAt(new Date());
        evaluation.setStars(3);
        evaluation.setComment("Sujper");

        Evaluation newEvaluation = evaluationService.create(evaluation,1,1);
        Assertions.assertEquals(evaluation.getCreatedAt(),newEvaluation.getCreatedAt());
        Assertions.assertEquals(evaluation.getStars(),newEvaluation.getStars());
        Assertions.assertEquals(evaluation.getComment(),newEvaluation.getComment());


    }

    @Test
    public void testDeleteEvaluation(){
        Evaluation evaluation = new Evaluation();
        evaluation.setCreatedAt(new Date());
        evaluation.setStars(3);
        evaluation.setComment("Sujper");

        Evaluation newEvaluation = evaluationService.create(evaluation,1,1);

        evaluationService.delete(newEvaluation.getId());

        Assertions.assertNull(evaluationService.getById(1));

    }

}
