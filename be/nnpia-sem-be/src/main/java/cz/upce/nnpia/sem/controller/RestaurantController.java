package cz.upce.nnpia.sem.controller;

import cz.upce.nnpia.sem.dto.EvaluationDto;
import cz.upce.nnpia.sem.dto.RestaurantDto;
import cz.upce.nnpia.sem.entity.Evaluation;
import cz.upce.nnpia.sem.entity.Photo;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.service.EvaluationService;
import cz.upce.nnpia.sem.service.PhotoService;
import cz.upce.nnpia.sem.service.RestaurantService;
import cz.upce.nnpia.sem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/restaurant")
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final EvaluationService evaluationService;
    private final UserService userService;
    private final PhotoService photoService;

    public RestaurantController(RestaurantService restaurantService, EvaluationService evaluationService, UserService userService, PhotoService photoService) {
        this.restaurantService = restaurantService;
        this.evaluationService = evaluationService;
        this.userService = userService;
        this.photoService = photoService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(@RequestPart("restaurant") RestaurantDto restaurantDto, @RequestPart("photoTitle") MultipartFile photoTitle) {
        Photo photo = null;
        try {
            photo = photoService.createPhoto(photoTitle, null, null);
        } catch (IOException ex) {
            photo = null;
        }
        Restaurant rest = new Restaurant();
        rest.setName(restaurantDto.getName());
        rest.setNote(restaurantDto.getNote());
        rest.setPhoto(photo);
        rest.setAddress(restaurantDto.getAddress());
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if(auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User){
             user = userService.getByEmail(((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername());
        }
        if(user==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        rest.setAdmin(user);
        Restaurant createdRestaurant = restaurantService.createRestaurant(rest);
        if (createdRestaurant == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(convertToDto(createdRestaurant), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/search/{filter}")
    public ResponseEntity<?> filter(@PathVariable String filter){
        List<Restaurant> filteredRestaurants = restaurantService.filterRestaurants(filter);
        if(filteredRestaurants==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(filteredRestaurants.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }
    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Restaurant findedRestaurant = restaurantService.getById(id);
        if (findedRestaurant == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(findedRestaurant), HttpStatus.OK);
    }
    @Transactional
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Restaurant> findedRestaurants = restaurantService.getAllRestaurants();
        if (findedRestaurants == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedRestaurants.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('ROLE_Admin')")
    public ResponseEntity<?> getAllByAdminEmail(@PathVariable String email) {
        List<Restaurant> findedRestaurants = restaurantService.getAllByAdminEmail(email);
        if (findedRestaurants == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(findedRestaurants.stream().map(this::convertToDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Restaurant deletedRestaurant = restaurantService.delete(id);
        if (deletedRestaurant == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(deletedRestaurant), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody RestaurantDto restaurantDto) {
        Restaurant updatedRestaurant = restaurantService.update(id, convertToEntity(restaurantDto));
        if (updatedRestaurant == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(convertToDto(updatedRestaurant), HttpStatus.OK);
    }

    private RestaurantDto convertToDto(Restaurant restaurant) {
        ModelMapper modelMapper = new ModelMapper();
        RestaurantDto dto = modelMapper.map(restaurant, RestaurantDto.class);
        if(restaurant.getPhoto()!=null){
            dto.setPhotoId(restaurant.getPhoto().getId());
        }
        List<Evaluation> evaluations = evaluationService.getAllEvaluationsToRestaurant(restaurant.getId());
        List<EvaluationDto> evalDto = new ArrayList<>();
        for(Evaluation eval:evaluations){
            evalDto.add(modelMapper.map(eval,EvaluationDto.class));
        }
        dto.setEvaluation(evalDto);
        Float avgStars = evaluationService.getAvgStarsToRestaurant(restaurant.getId());
        if(avgStars==null) dto.setStars(0);
        else dto.setStars(Math.round(avgStars));

        return dto;
    }

    private Restaurant convertToEntity(RestaurantDto restaurantDto) {
        ModelMapper modelMapper = new ModelMapper();
        Restaurant rest = modelMapper.map(restaurantDto, Restaurant.class);
        rest.setAdmin(userService.getById(restaurantDto.getAdminId()));
        return rest;
    }
}
