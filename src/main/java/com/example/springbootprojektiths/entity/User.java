package com.example.springbootprojektiths.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    private Long id;
    private String fullName;
    @Column(name = "userName", unique = true)
    private String userName;
    private String mail;
    private String imageBase64;

    public User() {
    }

    @Column(name = "image_data", columnDefinition = "LONGBLOB")

    @Lob
    private byte[] imageData;

    @OneToMany
    @JoinColumn(name = "person_id")
    List<Message> messages = new ArrayList<>();
}
