package com.example.springbootprojektiths;

import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.entity.User;
import com.example.springbootprojektiths.repository.UserRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateNewMessageForm {
    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Min(0)
    @Max(50)
    private String chatMessage;

    private boolean visible = false;

    //@ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
    private String username;

    public CreateNewMessageForm() {
    }


    public CreateNewMessageForm(String title, String chatMessage, boolean visible, User user) {
        this.title = title;
        this.chatMessage = chatMessage;
        this.visible = visible;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Message toEntity() {
        var message = new Message();
        message.setChatMessage(chatMessage);
        message.setTitle(title);
        message.setVisible(visible);
        message.setUser(user);
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
