package com.groupeC.whatsapp.controllers.auth;

import com.groupeC.whatsapp.models.dtos.requests.LoginRequest;
import com.groupeC.whatsapp.models.dtos.requests.RegisterRequest;
import com.groupeC.whatsapp.models.dtos.responses.AuthResponse;
import com.groupeC.whatsapp.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Classe pour la gestion des end points pour l'authentification
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    //injection des dépendances par constructeur grace à l'annotation @RequiredArgsConstructor
    private final AuthService authService;

    //endpoint d'authentification
    //pour l'inscription de l'utilisateur
    @PostMapping("/register")
    private ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));//Retourne le Dtos attendus provenant de la methode regiseter de AuthService
    }

    //Pour la connexion de l'utilisateur
    @PostMapping("/login")
    private ResponseEntity<AuthResponse> register(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));//Retourne le Dtos attendus provenant de la methode login de AuthService
    }
}
