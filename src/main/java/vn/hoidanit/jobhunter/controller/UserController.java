package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@RequestBody User postManUser) {
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User kUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(kUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) throws IdInvalidException {

        if (id >= 1000) {
            throw new IdInvalidException("Id ko lon hon 1000");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully! ");
    }

    // fetch user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User kUser = this.userService.handleGetUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(kUser);
    }

    // fetch all user
    @GetMapping("/users")
    public ResponseEntity<List<User>> fetchAllUsers() {
        List<User> l = this.userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User kUser = this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(kUser);
    }

}
