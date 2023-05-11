package cz.upce.nnpia.sem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.upce.nnpia.sem.dto.LoginDto;
import cz.upce.nnpia.sem.dto.RestaurantDto;
import cz.upce.nnpia.sem.entity.Restaurant;
import cz.upce.nnpia.sem.entity.Role;
import cz.upce.nnpia.sem.entity.User;
import cz.upce.nnpia.sem.security.JwtRequest;
import cz.upce.nnpia.sem.service.RestaurantService;
import cz.upce.nnpia.sem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@RunWith(SpringRunner.class)
public class RestaurantControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        User user = new User();
        user.setPassword("Heslo+123");
        user.setEmail("muj@email.cz");
        user.setFirstname("Name");
        user.setLastname("Lastname");
        user.setRole(Role.ROLE_Admin);
        user.setLastUpdate(new Date());
        userService.addUser(user);
    }


    @Test
    public void testCreateRestaurant() {
        Assertions.assertDoesNotThrow(() -> {
            RestaurantDto dto = new RestaurantDto();
            dto.setAddress("Address");
            dto.setName("Test restaurant");
            dto.setNote("Best restaurant");
            dto.setAdminId(1);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/restaurant")
                            .content(asJsonString(dto))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isUnauthorized());
        });

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
