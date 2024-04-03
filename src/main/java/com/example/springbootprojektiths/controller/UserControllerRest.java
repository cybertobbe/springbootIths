package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserControllerRest {

    UserRepository userRepository;

    public UserControllerRest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    @PostMapping("/user")
    ResponseEntity<Void> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
    // read/show user
    @GetMapping("/user")
    List<User> userList() {
        var user = userRepository.findAll();
        return user;
    }
    @GetMapping("/user/{id}")
    Optional user(@PathVariable("id") Long id){
       var user = userRepository.findById(id);
        return user;
    }

    // Delete User
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        userRepository.deleteById(id);

    }

    // Change User
    @PatchMapping("/user/{id}")
    ResponseEntity<Void> updateUser(@RequestBody User updatedUser, @PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update the existing user's fields with the data from the updatedUser object
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setMail(updatedUser.getMail());

            userRepository.save(existingUser);

               return ResponseEntity.noContent().build();
        } else {
            // If the user with the given ID does not exist, return a response with HTTP status code 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }



    //  name, username, password, profile pic, email



}
