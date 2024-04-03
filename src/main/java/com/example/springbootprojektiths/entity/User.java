package com.example.springbootprojektiths.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // behövs inte för github löser d
    private Long id;
    private String fullName;
    @Column(name = "userName", unique = true)
    private String userName;
    private String mail;


    public User() {
    }

    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    @Lob
    private byte[] imageData;


    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    //Lazy fetch
    @OneToMany
    @JoinColumn(name = "person_id")
    List<Message> messages = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setMessage(List<Message> messages) {
        this.messages = messages;
    }

}
