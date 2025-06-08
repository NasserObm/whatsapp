package com.groupeC.whatsapp.models.dtos.requests;
//Classe pour la récuperer la requête du login
public class LoginRequest {
    //Champ nécessaire pour la connexion
    private String email;
    private String password;

    //Constructeur
    public LoginRequest() {
    }

    //Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
