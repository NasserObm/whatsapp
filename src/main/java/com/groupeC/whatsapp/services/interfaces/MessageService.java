package com.groupeC.whatsapp.services.interfaces;

import com.groupeC.whatsapp.models.dtos.requests.MessageRequest;
import com.groupeC.whatsapp.models.dtos.responses.MessageResponse;

import java.util.List;

//inteface contenant toutes les methodes et fonctions concernant les messages
public interface MessageService {
    void sendMessage(MessageRequest messageRequest);//methode pour envoyer un message d'un mail à un autre
    List<MessageResponse> getConversation(String email2);//pour récupérer une conversation entre deux personnes
    void markAsSeen(Long messageId);//Faire passer un message du statut non vue à vue
    List<MessageResponse> getUnreadMessages(String email);//obtenir tous les messages qui n'ont pas encore été lu
}
