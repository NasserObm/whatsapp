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
    private final CryptoService cryptoService;

    @Override
    public void sendMessage(MessageRequest messageRequest) {
        String senderEmail = UserLogUtils.utilsConnecter();
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new ApiException("Utilisateur non reconnu"));
        if (!sender.getUserRole().equals(UserRole.USER)) {
            throw new ApiException("Action réservée aux utilisateurs");
        }
        User receiver = userRepository.findByEmail(messageRequest.getReceiverEmail())
                .orElseThrow(() -> new ApiException("Destinataire introuvable"));

        try {
            String encryptedContent = cryptoService.encrypt(messageRequest.getContent());
            MessageRequest encryptedMessageRequest = new MessageRequest();
            encryptedMessageRequest.setReceiverEmail(messageRequest.getReceiverEmail());
            encryptedMessageRequest.setContent(encryptedContent);

            Message message = messageMapper.toEntity(encryptedMessageRequest, senderEmail);
            messageRepository.save(message);

            if (!sender.getContacts().contains(receiver)) sender.getContacts().add(receiver);
            if (!receiver.getContacts().contains(sender)) receiver.getContacts().add(sender);
            userRepository.save(sender);
            userRepository.save(receiver);

            messagingTemplate.convertAndSendToUser(
                    receiver.getEmail(),
                    "/queue/messages",
                    encryptedMessageRequest
            );
        } catch (Exception e) {
            throw new ApiException("Erreur lors du chiffrement du message");
        }
    }

    @Override
    public List<MessageResponse> getConversation(String email2) {
        String email1 = UserLogUtils.utilsConnecter();
        User user = userRepository.findByEmail(email1).orElseThrow(() -> new ApiException("Utilisateur non reconnu"));
        if (!user.getUserRole().equals(UserRole.USER)) throw new ApiException("Action réservée aux utilisateurs");

        List<Message> messages = messageRepository.findConversation(email1, email2);
        return messages.stream()
                .map(message -> {
                    try {
                        String decryptedContent = cryptoService.decrypt(message.getContent());
                        MessageResponse response = messageMapper.toResponse(message);
                        response.setContent(decryptedContent);
                        return response;
                    } catch (Exception e) {
                        throw new RuntimeException("Erreur lors du déchiffrement");
                    }
                })
                .collect(Collectors.toList());
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
