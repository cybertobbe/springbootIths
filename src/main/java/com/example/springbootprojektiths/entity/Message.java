package com.example.springbootprojektiths.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String chatMessage;
    private String author;
    private boolean visible = false;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @CreatedDate
    private LocalDate date;


    public Message() {
        this.date = LocalDate.now();
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

    public String getMessage() {
        return chatMessage;
    }

    public void setMessage(String message) {
        this.chatMessage = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
