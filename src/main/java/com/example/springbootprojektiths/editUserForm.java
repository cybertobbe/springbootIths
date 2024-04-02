package com.example.springbootprojektiths;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class editUserForm {
    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String fullName;

    @Size(min = 1, max = 100)
    private String userName;
    @Size(min = 1, max = 100)
    private String mail;

    public editUserForm(Long id, String fullName, String userName, String mail) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.mail = mail;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        editUserForm other = (editUserForm) obj;
        return userName != null ? userName.equals(other.userName) : other.userName == null;
    }

    @Override
    public int hashCode() {
        return userName != null ? userName.hashCode() : 0;
    }
}
