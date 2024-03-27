package com.example.springbootprojektiths.controller;

import com.example.springbootprojektiths.CreateNewMessageForm;
import com.example.springbootprojektiths.EditMessageForm;
import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.repository.MessageRepository;
import com.example.springbootprojektiths.repository.UserRepository;
import com.example.springbootprojektiths.service.MessageServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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




    @RequestMapping(value = "/listmessages", method = RequestMethod.GET)
    public String listMessages(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @AuthenticationPrincipal OAuth2User principal) {

        //int githubid = principal.getAttribute("id");
        //long githubid = (long) githubid;

        Object idObject = principal.getAttribute("id");

        Integer idInteger = (Integer) idObject;
        var user = userRepository.findById(idInteger.longValue());
        model.addAttribute("person", user.get());

//        if (idObject instanceof Integer) {
//            Integer idInteger = (Integer) idObject;
//            var user = userRepository.findById(idInteger.longValue());
//            model.addAttribute("person", user.get());
//        } else if (idObject instanceof Long) {
//            var user = userRepository.findById((Long) idObject);
//            model.addAttribute("person", user.get());
//        }
        List<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
   //     var user = userRepository.findById(id);
  //      model.addAttribute("person", user.get());
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

/*    @GetMapping("/posts")
    String posts(Model model ){
       List<Message> messages = messageRepository.findAll();
       // List<User> people = userRepository.findAll();
        model.addAttribute("messages", messages);
        return "posts";
    }*/

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
        return "redirect:/listmessages";
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
            return "redirect:/listmessages";
        } else {
            // Handle case where message with given id is not found
            return "redirect:/error";
        }
    }



}


