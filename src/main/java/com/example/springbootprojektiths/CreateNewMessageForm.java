package com.example.springbootprojektiths;

import com.example.springbootprojektiths.entity.Message;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateNewMessageForm {
    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Min(0)
    @Max(50)
    private String chatMessage;

    public CreateNewMessageForm() {
    }

    public CreateNewMessageForm(String title, String chatMessage) {
        this.title = title;
        this.chatMessage = chatMessage;
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

    public Message toEntity() {
        var message = new Message();
        message.setChatMessage(chatMessage);
        message.setTitle(title);
        return message;
    }


}
