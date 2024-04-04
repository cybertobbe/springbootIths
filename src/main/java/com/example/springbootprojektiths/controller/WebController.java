package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.CreateNewMessageForm;
import com.example.springbootprojektiths.EditMessageForm;
import com.example.springbootprojektiths.config.ImageUploader;
import com.example.springbootprojektiths.editUserForm;
import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.MessageRepository;
import com.example.springbootprojektiths.repository.UserRepository;
import com.example.springbootprojektiths.service.MessageServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class WebController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageServices messageServices;

    @Autowired
    ImageUploader imageUploader;


    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepage(Model model) {
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
        return "homepage";
    }

    @RequestMapping(value = "/listmessages", method = RequestMethod.GET)
    public String listMessages(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @AuthenticationPrincipal OAuth2User principal) {

        Object idObject = principal.getAttribute("id");

        if (idObject instanceof Integer) {
            Integer idInteger = (Integer) idObject;
            Optional<User> userOptional = userRepository.findById(idInteger.longValue());

            userOptional.ifPresent(user -> model.addAttribute("person", user));
        } else {
            // Hantera fallet när id inte är en Integer
            return "redirect:/error";
        }

        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            byte[] imageData = message.getUser().getImageData();
            if (imageData != null) {
                String base64Image = Base64.getEncoder().encodeToString(imageData);
                message.getUser().setImageBase64(base64Image); // Assuming there's a setter for Base64 image in User class
            }
        }

        model.addAttribute("messages", messages);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Message> messagePage = messageServices.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("messagePage", messagePage);

        int totalPages = messagePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "posts";
    }

    @GetMapping("/createNewMessage")
    public String addMessage(Model model) {
        model.addAttribute("formData", new CreateNewMessageForm());
        return "createNewMessage";
    }

    @PostMapping("/createNewMessage")
    public String submitMessage(@Valid @ModelAttribute("formData") CreateNewMessageForm message, @AuthenticationPrincipal OAuth2User principal) {


        Object idObject = principal.getAttribute("id");

        Integer idInteger = (Integer) idObject;
        var user = userRepository.findById(idInteger.longValue());

        if (user != null) {
            message.setUser(user.get());
            messageRepository.save(message.toEntity());
        } else {
            throw new RuntimeException("not found");
        }

        return "redirect:/listmessages";
    }

    @GetMapping("/yourMessages")
    public String editMessages2(@AuthenticationPrincipal OAuth2User principal, Model model) {
        Object idObject = principal.getAttribute("id");
        Integer idInteger = (Integer) idObject;
        Optional<User> userOptional = userRepository.findById(idInteger.longValue());
        User user = userOptional.get();
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("userId", user.getId());
        model.addAttribute("messages", messages);
        return "yourMessages";

    }

    @GetMapping("/editMessage/{id}")
    public String editMessage(@PathVariable Long id, Model model) {
        Optional<Message> message = messageRepository.findById(id);
        model.addAttribute("formData", new EditMessageForm(message.get().getId(), message.get().getTitle(), message.get().getChatMessage()));
        return "editMessage";
    }

    @PostMapping("/editMessage/{id}")
    public String submitEditMessage(@PathVariable Long id, Model model, @ModelAttribute("formData") EditMessageForm messageForm) {
        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();

            // Update message attributes based on form data
            message.setTitle(messageForm.getTitle());
            message.setChatMessage(messageForm.getChatMessage());

            // Save the updated message
            messageRepository.save(message);

            // Redirect to a different page or return appropriate view name
            return "redirect:/listmessages";
        } else {
            // Handle case where message with given id is not found
            return "redirect:/error";
        }
    }

    @GetMapping("/userSettings")
    public String userPage(@AuthenticationPrincipal OAuth2User principal, Model model) {
        Object idObject = principal.getAttribute("id");
        Integer idInteger = (Integer) idObject;
        Optional<User> userOptional = userRepository.findById(idInteger.longValue());
        User user = userOptional.get();

        if (user.getImageData() != null) {
            String base64Image = Base64.getEncoder().encodeToString(user.getImageData());
            model.addAttribute("imageData", base64Image);
        }
        model.addAttribute("userData", new editUserForm(user.getId(), user.getFullName(), user.getUserName(), user.getMail()));
        return "userSettings";
    }

    @PostMapping("/userSettings")
    public String editUser(@AuthenticationPrincipal OAuth2User principal, @ModelAttribute("userData") editUserForm userForm) {
        Object idObject = principal.getAttribute("id");
        Integer idInteger = (Integer) idObject;
        Optional<User> userOptional = userRepository.findById(idInteger.longValue());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setFullName(userForm.getFullName());
            user.setUserName(userForm.getUserName());
            user.setMail(userForm.getMail());

            userRepository.save(user);
            return "userSettings";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, @AuthenticationPrincipal OAuth2User principal) throws IOException {
        Object idObject = principal.getAttribute("id");
        Integer idInteger = (Integer) idObject;
        Optional<User> userOptional = userRepository.findById(idInteger.longValue());
        User user = userOptional.get();
        user.setImageData(file.getBytes());
        userRepository.save(user);
        return "redirect:/userSettings";
    }
}