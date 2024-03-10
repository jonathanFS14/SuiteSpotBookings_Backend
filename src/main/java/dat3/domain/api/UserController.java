package dat3.domain.api;

import dat3.domain.dto.UserRequest;
import dat3.domain.dto.UserResponse;
import dat3.domain.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse addUser(@RequestBody UserRequest body){
        return userService.addUser(body, false);
    }

    @GetMapping()
    UserResponse getUser(Principal principal){
        return userService.getUser(principal.getName(), false);
    }

    @GetMapping("/all")
    List<UserResponse> getAllUsers() {
        return userService.getAllUsers(false);
    }

    @PutMapping()
    UserResponse editUser(@RequestBody UserRequest body, Principal principal) {
        return userService.editUser(body, principal.getName());
    }

    @DeleteMapping()
    ResponseEntity<Boolean> deleteUser(Principal principal) {
        return userService.deleteUser(principal.getName());
    }
}
