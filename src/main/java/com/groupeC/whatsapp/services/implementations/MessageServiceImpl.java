package com.groupeC.whatsapp.services.implementations;

import com.groupeC.whatsapp.exceptions.ApiException;
import com.groupeC.whatsapp.models.dtos.requests.MessageRequest;
import com.groupeC.whatsapp.models.dtos.responses.MessageResponse;
import com.groupeC.whatsapp.models.entities.Message;
import com.groupeC.whatsapp.models.entities.User;
import com.groupeC.whatsapp.models.enums.UserRole;
import com.groupeC.whatsapp.models.mappers.MessageMapper;
import com.groupeC.whatsapp.repositories.MessageRepository;
import com.groupeC.whatsapp.repositories.UserRepository;
import com.groupeC.whatsapp.services.interfaces.ContactService;
import com.groupeC.whatsapp.services.interfaces.MessageService;
import com.groupeC.whatsapp.utils.UserLogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Classe pour implementer toutes les methodes
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    //injection de dépendance grace à @RequiredArgsConstructor
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    @Override
    public void sendMessage(MessageRequest messageRequest) {
        String senderEmail = UserLogUtils.utilsConnecter(); // Email de l'utilisateur connecté

        // Récupération de l'expéditeur
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new ApiException("Utilisateur non reconnu"));

        // Vérification du rôle
        if (!sender.getUserRole().equals(UserRole.USER)) {
            throw new ApiException("Action réservée aux utilisateurs");
        }

        // Récupération du destinataire
        User receiver = userRepository.findByEmail(messageRequest.getReceiverEmail())
                .orElseThrow(() -> new ApiException("Destinataire introuvable"));

        // Création du message
        Message message = messageMapper.toEntity(messageRequest, senderEmail);
        messageRepository.save(message);

        // 🔥 Ajout mutuel dans les contacts s'ils ne sont pas déjà liés
        if (!sender.getContacts().contains(receiver)) {
            sender.getContacts().add(receiver);
        }
        if (!receiver.getContacts().contains(sender)) {
            receiver.getContacts().add(sender);
        }

        // Sauvegarder les deux utilisateurs mis à jour
        userRepository.save(sender);
        userRepository.save(receiver);

        // 💬 Envoi du message via WebSocket
        messagingTemplate.convertAndSendToUser(
                receiver.getEmail(),
                "/queue/messages",
                messageRequest
        );
    }

    @Override
    public List<MessageResponse> getConversation(String email2) {
        String email1= UserLogUtils.utilsConnecter();//récupération de l'email
        //Verification de l'existence du mail en db
        User user= userRepository.findByEmail(email1).orElseThrow(
                ()->new ApiException("Utilisateur non reconnu")
        );
        //Condition pour verifier le role de l'utilisateur
        if (!user.getUserRole().equals(UserRole.USER)){
            throw new ApiException("Action reservé au Utilisateur");
        }
        return messageRepository.findConversation(email1, email2)
                .stream()
                .map(messageMapper::toResponse)
                .toList();
    }
    public void markAsSeen(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message introuvable"));
        message.setSeen(true);
        messageRepository.save(message);
    }

    @Override
    public List<MessageResponse> getUnreadMessages(String email) {
        List<Message> unreadMessages = messageRepository.findByReceiverEmailAndSeenFalse(email);
        return unreadMessages.stream()
                .map(messageMapper::toResponse)
                .collect(Collectors.toList());
    }

}
