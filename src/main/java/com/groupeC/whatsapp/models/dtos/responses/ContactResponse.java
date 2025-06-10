package com.groupeC.whatsapp.models.dtos.responses;

import com.groupeC.whatsapp.models.enums.UserRole;

public class ContactResponse {
    private String email;
    private String name;
    private UserRole role;
    private boolean connected;

    public ContactResponse(String email, String name, UserRole role, boolean connected) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.connected=connected;
    }

    public ContactResponse() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
