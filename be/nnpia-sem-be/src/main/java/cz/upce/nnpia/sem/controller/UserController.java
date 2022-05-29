package cz.upce.nnpia.sem.controller;

import cz.upce.nnpia.sem.dto.AuthorizationDto;
import cz.upce.nnpia.sem.dto.LoginDto;
import cz.upce.nnpia.sem.dto.UserDto;
import cz.upce.nnpia.sem.entity.Role;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllUser(){
        List<User> users = userService.getAll();
        if(users==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<UserDto> userDtos = users.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        User user = userService.getById(id);
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserDto userDto = convertToDto(user);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto, HttpServletResponse response) throws ParseException {
        User user = userService.addUser(convertToEntity(userDto));
        if(user==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserDto createdUser = convertToDto(user);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UserDto userDto,@PathVariable int id) throws ParseException {
        User updatedUser = userService.updateUser(convertToEntity(userDto),id);
        if(updatedUser==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserDto updatedUserDto =  convertToDto(updatedUser);
        return new ResponseEntity<>(updatedUserDto,HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<?> delete(@PathVariable int id){
        User deletedUser = userService.deleteUser(id);
        if(deletedUser==null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UserDto userDto = convertToDto(deletedUser);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        User user = userService.loginUser(loginDto.getEmail(),loginDto.getPassword());
        if(user==null) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        LoginDto response = new LoginDto();
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private UserDto convertToDto(User user){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }
}
