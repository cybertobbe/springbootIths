package com.example.springbootprojektiths;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
