package cz.upce.nnpia.sem.controller;

import cz.upce.nnpia.sem.dto.LoginDto;
import cz.upce.nnpia.sem.dto.UserDto;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.security.JwtRequest;
import cz.upce.nnpia.sem.security.JwtTokenUtil;
import cz.upce.nnpia.sem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;


    public UserController(UserService userService, JwtTokenUtil jwtTokenUtil, AuthenticationManager manager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = manager;
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
    private ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final User user = userService.loginUser(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(user.getEmail());

        return new ResponseEntity<>(new LoginDto(user.getEmail(),token,user.getRole()), HttpStatus.OK);
    }

    private UserDto convertToDto(User user){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userDto, User.class);
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
