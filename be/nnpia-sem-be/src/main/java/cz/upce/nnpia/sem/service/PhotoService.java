package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.Photo;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.repository.PhotoRepository;
import cz.upce.nnpia.sem.repository.RestaurantRepository;
import cz.upce.nnpia.sem.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public PhotoService(PhotoRepository photoRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Photo createPhoto(MultipartFile file, Integer userId, Integer restaurantId) throws IOException {
        User user = null;
        Restaurant restaurant = null;
        if (userId != null) {
            user = userRepository.getUserByIdAndDeletedAtIsNull(userId);
        }
        if (restaurantId != null) {
            restaurant = restaurantRepository.getByIdAndDeletedAtIsNull(restaurantId);
        }


        Photo photo = new Photo();
        photo.setUser(user);
        photo.setPhoto(file.getBytes());
        photo.setFilename(file.getOriginalFilename());
        photo.setPhotoType(file.getContentType());
        photo.setRestaurant(restaurant);
        return photoRepository.save(photo);


    }
}
