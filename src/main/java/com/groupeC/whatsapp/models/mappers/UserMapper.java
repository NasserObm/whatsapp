package com.groupeC.whatsapp.models.mappers;

import com.groupeC.whatsapp.models.dtos.requests.RegisterRequest;
import com.groupeC.whatsapp.models.dtos.responses.ContactResponse;
import com.groupeC.whatsapp.models.entities.User;
import com.groupeC.whatsapp.models.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//Classe permettant de convertir les données dtos des utilisateurs en entité et inversement
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    //convertion du dtos en entité
    public User toEntity(RegisterRequest registerRequest){
        User user=new User();//création du user vide
        user.setName(registerRequest.getName());//ajout du nom provenant du dto dans la variable name de l'utilisateur
        user.setEmail(registerRequest.getEmail());//ajout de l'email provenant du dto dans la variable email de l'utilisateur
        user.setUserRole(UserRole.USER);//création par défaut d'un utilisateur simple
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));//enregistrement chiffré du mot de passe ce qui nous empêche de connaitre le mot de passe et protège l'entreprise dans l'intégrité
        //ajouter en cas d'ajout de nouvelle variable pour un utilisateur
        return user;//renvoie de l'utilisateur
    }

    //fonction pour la convertion de l'entité vers le dtos n'est pas encore necessaire
    public ContactResponse toDtos(User user,boolean connected){
        ContactResponse contactResponse=new ContactResponse();
        contactResponse.setEmail(user.getEmail());
        contactResponse.setName(user.getName());
        contactResponse.setRole(user.getUserRole());
        contactResponse.setConnected(connected);
        return contactResponse;
    }
}
