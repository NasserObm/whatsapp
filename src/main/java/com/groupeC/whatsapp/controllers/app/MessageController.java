package com.groupeC.whatsapp.controllers.app;

import com.groupeC.whatsapp.models.dtos.requests.MessageRequest;
import com.groupeC.whatsapp.models.dtos.responses.MessageResponse;
import com.groupeC.whatsapp.securities.JwtService;
import com.groupeC.whatsapp.services.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Classe pour la gestion de tous les endpoints concernant les messages
 */
@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class MessageController {
    /**
     * Injection de dépendance par constructeur grace à @RequiredArgsConstructor
     */
    private final MessageService messageService;
    private final JwtService jwtService;

    /**
     * Ce endpoint reçoit les messages envoyés par WebSocket à /app/send-message
     */
    @PostMapping("/send-message")
    public void handleSendMessage(MessageRequest messageRequest) {
        messageService.sendMessage(messageRequest);
    }
    /**
     * Ce endpoint récupère les messages entre deux personnes
     */
    @GetMapping("/conversation")
    public ResponseEntity<List<MessageResponse>> getConversation(
            @RequestParam String user2
    ) {
        return ResponseEntity.ok(messageService.getConversation(user2));
    }
/*
    @GetMapping("/unread")
    public ResponseEntity<List<MessageResponse>> getUnreadMessages(
            @RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.substring(7));
        return ResponseEntity.ok(messageService.getUnreadMessages(email));
    }

    @PostMapping("/{id}/seen")
    public ResponseEntity<?> markAsSeen(@PathVariable Long id) {
        messageService.markAsSeen(id);
        return ResponseEntity.ok("Vu !");
    }
*/
}
