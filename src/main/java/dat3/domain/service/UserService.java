package dat3.domain.service;

import dat3.domain.dto.UserRequest;
import dat3.domain.dto.UserResponse;
import dat3.domain.entity.Reservation;
import dat3.domain.entity.User;
import dat3.domain.repository.UserRepository;
import dat3.security.entity.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse addUser(UserRequest body, boolean includeAll) {
        if (userRepository.existsById(body.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }
        if (userRepository.existsByEmail(body.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        User user = UserRequest.getUserEntity(body);
        user.addRole(Role.USER);
        userRepository.save(user);
        return new UserResponse(user, includeAll);
    }

    public UserResponse getUser(String id, boolean b) {
        return new UserResponse(getUserById(id), b);
    }

    public List<UserResponse> getAllUsers(boolean b) {
        List<User> users = userRepository.findAll();
        return users.stream().map(((User) -> new UserResponse(User, b))).toList();
    }

    public UserResponse editUser(UserRequest body, String id) {
        User user = getUserById(id);
        user.setFirstName(body.getFirstName());
        user.setLastName(body.getLastName());
        if (!user.getEmail().equals(body.getEmail()) && userRepository.existsByEmail(body.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }
        user.setEmail(body.getEmail());
        user.setStreet(body.getStreet());
        user.setCity(body.getCity());
        user.setZip(body.getZip());
        user.setPhoneNumber(body.getPhoneNumber());
        userRepository.save(user);
        return new UserResponse(user, false);
    }

    public ResponseEntity<Boolean> deleteUser(String id) {
        User user = getUserById(id);
        if (!user.getReservations().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User has reservations");
        }
        userRepository.delete(user);
        return ResponseEntity.ok(true);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this id does not exist"));
    }
}
