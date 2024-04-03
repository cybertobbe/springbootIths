package com.example.springbootprojektiths.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Date;


@Entity
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String chatMessage;
    private boolean visible = false;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @CreatedDate
    private Instant createdDate;

    // Spårar när meddelandet senast ändrades
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    public Message() {

    }

//For no "last modified date" when creating a message
//
//    @PrePersist
//    protected void onCreate() {
//        Date createdOn = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        Date lastUpdate = new Date();
//    }

    public String getUserName(){
        return this.user.getUserName();
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
