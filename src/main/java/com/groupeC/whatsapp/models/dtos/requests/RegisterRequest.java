package com.groupeC.whatsapp.models.dtos.requests;
//Classe pour récupérer les requêtes pour la création d'un utilisateur
public class RegisterRequest {
    //Champ valide pour la création d'un compte
    private String email;
    private String name;
    private String password;

    //constructeur
    public RegisterRequest() {
    }

    //Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
