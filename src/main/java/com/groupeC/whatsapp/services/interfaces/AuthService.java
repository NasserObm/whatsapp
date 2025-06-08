package com.groupeC.whatsapp.services.interfaces;

import com.groupeC.whatsapp.models.dtos.requests.LoginRequest;
import com.groupeC.whatsapp.models.dtos.requests.RegisterRequest;
import com.groupeC.whatsapp.models.dtos.responses.AuthResponse;

public interface AuthService {
    //interface énumérant tous les services concernant la gestion de l'authentification
    AuthResponse register(RegisterRequest registerRequest);//fonction pour l'inscription de l'utilisateur
    AuthResponse login(LoginRequest loginRequest);//fonction pour la connexion de l'utilisateur
}
