package dat3.domain.service;

import dat3.domain.dto.UserRequest;
import dat3.domain.entity.User;
import dat3.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    UserService userService;

    User u1;

    boolean isInitialized = false;
    @BeforeEach
    void setUp() {
        if (!isInitialized) {
            u1 = new User();
            u1.setUsername("u1");
            u1.setPassword("p1");
            u1.setEmail("e1");
           userRepository.save(u1);
            userService = new UserService(userRepository);
            isInitialized = true;
        }
    }

    @Test
    void addUser() {
        UserRequest userRequest = new UserRequest("john_doe", "yourSecurePassword","john.doe@example.com", "John", "Doe", "123 Main Street", "Anytown", "12345", "+1234567890");
        userService.addUser(userRequest, false);
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    void AddUserFailUsernameAlreadyExist() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("u1");
        userRequest.setPassword("p2");
        userRequest.setEmail("e2");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> userService.addUser(userRequest, false));
        assertEquals(/*HttpStatus.BAD_REQUEST*/ HttpStatusCode.valueOf(400), ex.getStatusCode());
    }
}