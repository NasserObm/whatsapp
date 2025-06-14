package com.groupeC.whatsapp.services.implementations;

import com.groupeC.whatsapp.models.dtos.requests.LoginRequest;
import com.groupeC.whatsapp.models.dtos.requests.RegisterRequest;
import com.groupeC.whatsapp.models.dtos.responses.AuthResponse;
import com.groupeC.whatsapp.models.entities.User;
import com.groupeC.whatsapp.models.mappers.UserMapper;
import com.groupeC.whatsapp.repositories.UserRepository;
import com.groupeC.whatsapp.securities.JwtService;
import com.groupeC.whatsapp.services.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

//Classe pour l'implementation des différents service AuthService
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    //injection des dépendances par constrcteur grace à l'annotation @RequiredArgsConstructor
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    //Implementation des methodes de l'interface

    private boolean isPasswordStrong(String password) {
        if (password == null) return false;
        if (password.length() < 8) return false;

        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(ch -> "!@#$%^&*()_+-=[]{}|;:'\",.<>/?".indexOf(ch) >= 0);

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    //fonction pour l'inscription d'un utilisateur
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (!isPasswordStrong(request.getPassword())) {
            throw new IllegalArgumentException("Mot de passe trop faible : au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial sont requis.");
        }
        User user = userMapper.toEntity(request);//récupération de l'élément de sorti de la methode toEntity dans la classe UserMapper
        userRepository.save(user);//sauvegarde en base de de donnée de l'utilisateur

        String token = jwtService.generateToken(user.getEmail());//génération du token
        return new AuthResponse(token);//retour de la methode attendu
    }

    //fonction pour la connexion d'un utilisateur
    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );//verification des infos fourni par le dtos en db

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));//Gestion de l'erreur en cas d'email non trouvé renvoie une erreur
        //Si l'exception n'est pas detectée continuer

        String token = jwtService.generateToken(user.getEmail());//Géneration du token
        return new AuthResponse(token);//Retour attendu par la fonction
    }
}
