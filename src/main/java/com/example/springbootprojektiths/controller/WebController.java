package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.CreateNewMessageForm;
import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.repository.MessageRepository;
import com.example.springbootprojektiths.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
}


