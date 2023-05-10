package cz.upce.nnpia.sem.service;

import cz.upce.nnpia.sem.entity.Role;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    private final String EMAIL = "muj@email.cz";

    @Test
    public void testAddUserWithValidPassoword(){
        User user = new User();
        user.setPassword("Heslo+123");
        user.setEmail(EMAIL);
        user.setFirstname("Name");
        user.setLastname("Lastname");
        user.setRole(Role.ROLE_User);
        user.setLastUpdate(new Date());
        User newUser = userService.addUser(user);
        Assertions.assertEquals(user.getEmail(),newUser.getEmail());
        Assertions.assertEquals(user.getFirstname(),newUser.getFirstname());
        Assertions.assertEquals(user.getLastname(),newUser.getLastname());
        Assertions.assertEquals(user.getRole(),newUser.getRole());
    }

    @Test
    public void testAddUserWithInvalidPassoword(){
        User user = new User();
        user.setPassword("1234");
        user.setEmail(EMAIL);
        user.setFirstname("Name");
        user.setLastname("Lastname");
        user.setRole(Role.ROLE_User);
        user.setLastUpdate(new Date());
        User newUser = userService.addUser(user);
        Assertions.assertNull(newUser);
    }

    @Test
    public void testAddUserWithValidEmail(){
        User user = new User();
        user.setPassword("Heslo+123");
        user.setEmail(EMAIL);
        user.setFirstname("Name");
        user.setLastname("Lastname");
        user.setRole(Role.ROLE_User);
        user.setLastUpdate(new Date());
        User newUser = userService.addUser(user);
        Assertions.assertEquals(user.getEmail(),newUser.getEmail());
        Assertions.assertEquals(user.getFirstname(),newUser.getFirstname());
        Assertions.assertEquals(user.getLastname(),newUser.getLastname());
        Assertions.assertEquals(user.getRole(),newUser.getRole());
    }

    @Test
    public void testAddUserWithInvalidEmail(){
        User user = new User();
        user.setPassword("Heslo+123");
        user.setEmail("mujemail");
        user.setFirstname("Name");
        user.setLastname("Lastname");
        user.setRole(Role.ROLE_User);
        user.setLastUpdate(new Date());
        User newUser = userService.addUser(user);
        Assertions.assertNull(newUser);
    }
}
