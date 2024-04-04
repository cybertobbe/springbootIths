package com.example.springbootprojektiths;

import com.example.springbootprojektiths.entity.Message;
import com.example.springbootprojektiths.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNewMessageForm {
    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Min(0)
    @Max(50)
    private String chatMessage;
    private boolean visible = false;
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

    public Message toEntity() {
        var message = new Message();
        message.setChatMessage(chatMessage);
        message.setTitle(title);
        message.setVisible(visible);
        message.setUser(user);
        return message;
    }
}
