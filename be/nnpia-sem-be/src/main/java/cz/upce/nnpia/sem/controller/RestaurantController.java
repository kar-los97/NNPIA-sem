package cz.upce.nnpia.sem.controller;

import cz.upce.nnpia.sem.dto.RestaurantDto;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.service.RestaurantService;
import cz.upce.nnpia.sem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/restaurant")
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final UserService userService;

    public RestaurantController(RestaurantService restaurantService,UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RestaurantDto restaurantDto){
        Restaurant createdRestaurant = restaurantService.createRestaurant(convertToEntity(restaurantDto));
        if(createdRestaurant==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(createdRestaurant),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        Restaurant findedRestaurant = restaurantService.getById(id);
        if(findedRestaurant==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(findedRestaurant),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Restaurant> findedRestaurants = restaurantService.getAllRestaurants();
        if(findedRestaurants==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedRestaurants.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.OK);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getAllByAdminEmail(@PathVariable String email){
        List<Restaurant> findedRestaurants = restaurantService.getAllByAdminEmail(email);
        if(findedRestaurants==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedRestaurants.stream().map(this::convertToDto).collect(Collectors.toList()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        Restaurant deletedRestaurant = restaurantService.delete(id);
        if(deletedRestaurant==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(deletedRestaurant),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody RestaurantDto restaurantDto){
        Restaurant updatedRestaurant = restaurantService.update(id,convertToEntity(restaurantDto));
        if(updatedRestaurant==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(updatedRestaurant),HttpStatus.OK);
    }

    private RestaurantDto convertToDto(Restaurant restaurant){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    private Restaurant convertToEntity(RestaurantDto restaurantDto) {
        ModelMapper modelMapper = new ModelMapper();
        Restaurant rest = modelMapper.map(restaurantDto, Restaurant.class);
        rest.setAdmin(userService.getById(restaurantDto.getAdminId()));
        return rest;
    }
}
