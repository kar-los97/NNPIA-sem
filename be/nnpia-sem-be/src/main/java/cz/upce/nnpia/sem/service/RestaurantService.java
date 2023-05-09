package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;


    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants(){
        return restaurantRepository.findAllByDeletedAtIsNull();
    }

    public List<Restaurant> getAllByAdminEmail(String email){
        return restaurantRepository.findAllByDeletedAtIsNullAndAdmin_Email(email);
    }

    public Restaurant getById(int id){
        return restaurantRepository.getByIdAndDeletedAtIsNull(id);
    }

    public Restaurant update(int id, Restaurant restaurant){
        Restaurant restaurantToUpdate = restaurantRepository.getById(id);
        restaurantToUpdate.setAddress(restaurant.getAddress());
        restaurantToUpdate.setName(restaurant.getName());
        restaurantToUpdate.setNote(restaurant.getNote());
        restaurantToUpdate.setAdmin(restaurant.getAdmin());
        return restaurantRepository.save(restaurantToUpdate);
    }

    public Restaurant delete(int id){
        Restaurant deletedRestaurant = restaurantRepository.getById(id);
        deletedRestaurant.setDeletedAt(new Date());
        return restaurantRepository.save(deletedRestaurant);
    }


    public List<Restaurant> filterRestaurants(String filter) {
        return restaurantRepository.searchAllByAddressContainsOrNameContainsAndDeletedAtIsNull(filter,filter);
    }
}
