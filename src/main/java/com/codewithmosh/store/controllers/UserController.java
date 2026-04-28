package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.UserDTO;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.exceptions.UserNotFoundException;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    private UserService userService;

    @GetMapping()
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO) {
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Users with id " + id + " is not found "));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        User userToDelete = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Users with id " + id + " is not found "));
        userRepository.delete(userToDelete);
        return ResponseEntity.ok()
                .body("User with id " + id + " deleted successfully");
    }

}
