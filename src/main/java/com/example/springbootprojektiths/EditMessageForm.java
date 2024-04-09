package com.example.springbootprojektiths;

import com.example.springbootprojektiths.entity.Message;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditMessageForm {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Min(0)
    @Max(50)
    private String chatMessage;

    @Lob
    private byte[] imageData;

    public EditMessageForm() {
    }

    public EditMessageForm(Long id, String title, String chatMessage) {
        this.id = id;
        this.title = title;
        this.chatMessage = chatMessage;
    }

    public EditMessageForm(byte[] imageData) {
        this.imageData = imageData;
    }

    public Message toEntity() {
        var message = new Message();
        message.setId(id);
        message.setChatMessage(chatMessage);
        message.setTitle(title);
        return message;
    }
}

