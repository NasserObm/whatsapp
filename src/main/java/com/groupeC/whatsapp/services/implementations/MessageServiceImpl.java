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
        String email= UserLogUtils.utilsConnecter();//récupération de l'email
        //Verification de l'existence du mail en db
        User user= userRepository.findByEmail(email).orElseThrow(
                ()->new ApiException("Utilisateur non reconnu")
        );
        //Condition pour verifier le role de l'utilisateur
        if (!user.getUserRole().equals(UserRole.USER)){
            throw new ApiException("Action reservé au Utilisateur");
        }
        Message message=messageMapper.toEntity(messageRequest,email);//Remplissage d'un utilisateur avec le dtos par la convertion ToEntity dans MessageMapper
        messageRepository.save(message);//sauvegarde en base de donnée
        messagingTemplate.convertAndSendToUser(
                messageRequest.getReceiverEmail(),
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
