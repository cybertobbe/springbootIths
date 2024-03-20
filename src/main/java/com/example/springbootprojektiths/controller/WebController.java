package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/posts")
    String posts(Model model ){
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "posts";
    }

}
