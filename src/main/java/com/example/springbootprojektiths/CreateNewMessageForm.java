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

    private boolean visible = false;

    public CreateNewMessageForm() {
    }

    public CreateNewMessageForm(String title, String chatMessage, boolean visible) {
        this.title = title;
        this.chatMessage = chatMessage;
        this.visible = visible;
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
        return message;
    }

}
