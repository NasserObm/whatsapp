package com.groupeC.whatsapp.models.dtos.responses;
//Classe qui contiendra le token et les différentes information pour confirmer répôndre à la confirmation d'un login ou register
public class AuthResponse {
    //Champ nécessaire pour la classe
    private String token;//Le token qui sera gérer dans la classe AuthServiceImpl

    //Constructeur
    public AuthResponse(String token) {
        this.token = token;
    }

    public AuthResponse() {
    }

    //Getters et Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
