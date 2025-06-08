package com.groupeC.whatsapp.models.mappers;

import com.groupeC.whatsapp.models.dtos.requests.MessageRequest;
import com.groupeC.whatsapp.models.dtos.responses.MessageResponse;
import com.groupeC.whatsapp.models.entities.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//Classe pour la convertion des entités messages en dtos et inversement
@Component
public class MessageMapper {
    //Convertion de Dtos vers l'entité
    public Message toEntity(MessageRequest request,String email){
        Message message=new Message();//Création du message vide
        message.setContent(request.getContent());//ajout du contenu
        message.setReceiverEmail(request.getReceiverEmail());//ajout de l'email du receveur
        message.setSenderEmail(email);//ajout de l'email de l'envoyeur
        message.setTimestamp(LocalDateTime.now());//ajout de l'heure actuelle
        message.setSeen(false);
        //ajout des variables de convertion en cas d'ajout dans l'entité
        return message;//Type de retour attenduutils
    }

    //Convertion de l'entité vers le Dto
    public MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getSenderEmail(),
                message.getReceiverEmail(),
                message.getContent(),
                message.getTimestamp(),
                message.isSeen()
        );
    }

}
