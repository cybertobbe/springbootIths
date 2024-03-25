package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.CreateNewMessageForm;
import com.example.springbootprojektiths.EditMessageForm;
import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.repository.MessageRepository;
import com.example.springbootprojektiths.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;



    @GetMapping("/posts")
    String posts(Model model ){
       List<Message> messages = messageRepository.findAll();
       // List<User> people = userRepository.findAll();
        model.addAttribute("messages", messages);
        return "posts";
    }

    @GetMapping("/createNewMessage")
    public String addMessage (Model model) {
        model.addAttribute("formData", new CreateNewMessageForm());
        return "createNewMessage";
    }

   @PostMapping("/createNewMessage")
   public String submitMessage(@Valid @ModelAttribute("formData") CreateNewMessageForm message,
            BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return"createNewMessage";
        }
        messageRepository.save(message.toEntity());
        return "redirect:/posts";
    }

    @GetMapping("/yourMessages")
    public String editMessages(Model model){
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
                return "yourMessages";
    }

    @GetMapping("/editMessage/{id}")
    public String editMessage(@PathVariable Long id, Model model){
        Optional<Message> message = messageRepository.findById(id);
        model.addAttribute("formData", new EditMessageForm(message.get().getId(), message.get().getTitle(), message.get().getChatMessage()));
        return "editMessage";
    }

    @PostMapping("/editMessage/{id}")
    public String submitEditMessage(@PathVariable Long id, Model model, @ModelAttribute("formData") EditMessageForm messageForm){
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();

            // Update message attributes based on form data
            message.setTitle(messageForm.getTitle());
            message.setChatMessage(messageForm.getChatMessage());

            // Save the updated message
            messageRepository.save(message);

            // Redirect to a different page or return appropriate view name
            return "redirect:/posts";
        } else {
            // Handle case where message with given id is not found
            return "redirect:/error";
        }
    }

}


