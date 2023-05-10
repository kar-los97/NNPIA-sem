package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.Evaluation;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.repository.EvaluationRepository;
import cz.upce.nnpia.sem.repository.RestaurantRepository;
import cz.upce.nnpia.sem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    public EvaluationService(EvaluationRepository evaluationRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.evaluationRepository = evaluationRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Evaluation create(Evaluation evaluation, int restaurantId, Integer userId){
        Restaurant restaurant = restaurantRepository.getByIdAndDeletedAtIsNull(restaurantId);
        User user = null;
        if(userId!=null){
            user = userRepository.getUserByIdAndDeletedAtIsNull(userId);
        }
        evaluation.setUser(user);
        evaluation.setRestaurant(restaurant);
        evaluation.setCreatedAt(new Date());

        return evaluationRepository.save(evaluation);
    }

    public Evaluation getById(int id){
        return evaluationRepository.getByIdAndDeletedAtIsNull(id);
    }

    public List<Evaluation> getAllEvaluations(){
        return evaluationRepository.findAllByDeletedAtIsNull();
    }

    public List<Evaluation> getAllEvaluationsToRestaurant(int restaurantId){
        Restaurant restaurant = restaurantRepository.getByIdAndDeletedAtIsNull(restaurantId);
        return evaluationRepository.findAllByRestaurant(restaurant);
    }

    public Float getAvgStarsToRestaurant(int restaurantId){
        Restaurant restaurant = restaurantRepository.getByIdAndDeletedAtIsNull(restaurantId);
        return evaluationRepository.getAvgStarsToRestaurant(restaurant);
    }
    public List<Evaluation> getAllEvaluationsToUser(String userEmail){
        User user = userRepository.getUserByEmailAndDeletedAtIsNull(userEmail);
        return evaluationRepository.findAllByUserAndDeletedAtIsNullAndRestaurantDeletedAtIsNull(user);
    }

    public Evaluation update(Evaluation evaluation,int restaurantId, Integer userId, int id){
        Restaurant restaurant = restaurantRepository.getByIdAndDeletedAtIsNull(restaurantId);
        User user = null;
        if(userId!=null){
            user = userRepository.getUserByIdAndDeletedAtIsNull(userId);
        }
        Evaluation evaluationToUpdate = evaluationRepository.getByIdAndDeletedAtIsNull(id);
        if(evaluationToUpdate!=null){
            evaluationToUpdate.setRestaurant(restaurant);
            evaluationToUpdate.setUser(user);
            evaluationToUpdate.setComment(evaluation.getComment());
            evaluationToUpdate.setStars(evaluation.getStars());
            return evaluationRepository.save(evaluationToUpdate);
        }
        return null;
    }

    public Evaluation delete(int id){
        Evaluation evaluationToDelete = evaluationRepository.getByIdAndDeletedAtIsNull(id);
        if(evaluationToDelete!=null){
            evaluationToDelete.setDeletedAt(new Date());
            return evaluationRepository.save(evaluationToDelete);
        }
        return null;
    }
}
