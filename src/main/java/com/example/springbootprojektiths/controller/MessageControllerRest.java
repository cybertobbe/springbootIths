package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.MessageRepository;
import com.example.springbootprojektiths.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MessageControllerRest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;


    // get all messages
    @GetMapping("/message")
    List<Message> getMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

    // get message by id
    @GetMapping("/message/{id}")
    public Optional<Message> getMessage(@PathVariable("id") Long id) {
        var message = messageRepository.findById(id);

        return message;
    }

    // add message
    @PostMapping("/message/{id}")
    ResponseEntity<Void> createMessage(@PathVariable("id") Long id, @RequestBody Message message) {

        messageRepository.save(message);
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found with ID: " + id));


        List list = user.getMessages();
        list.add(message);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    // delete message
    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable("id") Long id) {
        messageRepository.deleteById(id);
    }

    // change message
    @PatchMapping("/message/change/{id}")
    ResponseEntity<String> changeMessage(@RequestBody Message updatedMessage, @PathVariable Long id) {

        // Retrieve the existing message from the repository
        var existingMessageOptional = messageRepository.findById(id);

        if (existingMessageOptional.isPresent()) {
            Message existingMessage = existingMessageOptional.get();
            existingMessage.setTitle(updatedMessage.getTitle());
            existingMessage.setChatMessage(updatedMessage.getChatMessage());
            existingMessage.setVisible(updatedMessage.isVisible());
            existingMessage.setUser(updatedMessage.getUser());
            existingMessage.setLastModifiedDate(Instant.from(LocalDateTime.now()));

            messageRepository.save(existingMessage);
            return ResponseEntity.ok("Message updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found with ID: " + id);
        }
    }

    // make message private
    @PutMapping("/message/{id}/setPrivate")
    ResponseEntity<Void> setVisible(@PathVariable Long id) {
        var count = messageRepository.setVisible(id);
        if (count == 1)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
