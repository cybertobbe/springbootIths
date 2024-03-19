package com.example.springbootprojektiths.restController;

import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.repository.MessageRepository;
import com.example.springbootprojektiths.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MessageControllerRest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

    // create test msg
    @GetMapping("/createTestMsg")
    Message testMsg(){
        Message message = new Message();
        String msg = "hello abc";
        message.setMessage(msg);
        messageRepository.save(message);
        return message;
    }

    // get all messages
    @GetMapping("/message")
    List<Message> getMessages(){
        // Retrieve all messages from the repository
        List<Message> messages = messageRepository.findAll();

        // Return the list of messages
        return messages;
    }

    // get message by id
    @GetMapping("/message/{id}")
    public Optional <Message> getMessage (@PathVariable ("id") Long id) {
       var message = messageRepository.findById(id);
        return message;
    }

    // add message
    @PostMapping("/message")
    ResponseEntity<Void> createMessage(@RequestBody Message message){
        messageRepository.save(message);
        return ResponseEntity.ok().build();
    }

    // delete message
    @DeleteMapping("/message/{id}")
    public void deleteMessage (@PathVariable ("id") Long id) {
        messageRepository.deleteById(id);
    }


    // change message
    @PatchMapping("/message/change/{id}")
    void changeMessage(@RequestBody Message updatedMessage,@PathVariable Long id){

        // Retrieve the existing message from the repository
        var existingMessageOptional = messageRepository.findById(id);

        // Check if the message with the given id exists
        if (existingMessageOptional.isPresent()) {
            // Update the existing message with the new data
            Message existingMessage = existingMessageOptional.get();
            existingMessage.setTitle(updatedMessage.getTitle());
            existingMessage.setMessage(updatedMessage.getMessage());
            existingMessage.setAuthor(updatedMessage.getAuthor());
            existingMessage.setVisible(updatedMessage.isVisible());
            existingMessage.setUser(updatedMessage.getUser());

            // Save the updated message back to the repository
            messageRepository.save(existingMessage);
        } else {
            // Handle the case where the message with the given id does not exist
            // You may choose to throw an exception or handle it differently based on your requirements
        }

    }
    
    // make message private
    @PutMapping("/message/{id}/setPrivate")
    ResponseEntity<Void> setVisible(@PathVariable Long id){
        var count = messageRepository.setVisible(id);
        if(count == 1)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}
