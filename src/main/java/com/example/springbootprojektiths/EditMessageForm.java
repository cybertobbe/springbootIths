package com.example.springbootprojektiths;

import com.example.springbootprojektiths.entity.Message;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EditMessageForm {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Min(0)
    @Max(50)
    private String chatMessage;

    public EditMessageForm() {
    }

    public EditMessageForm(Long id, String title, String chatMessage) {
        this.id = id;
        this.title = title;
        this.chatMessage = chatMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        message.setId(id);
        message.setChatMessage(chatMessage);
        message.setTitle(title);
        return message;
    }
}

