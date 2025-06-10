package com.groupeC.whatsapp.websockets;

import com.groupeC.whatsapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final UserRepository userRepository;

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = headerAccessor.getUser().getName(); // ou un header custom si besoin

        userRepository.findByEmail(email).ifPresent(user -> {
            user.setConnected(true);
            userRepository.save(user);
        });
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = headerAccessor.getUser().getName(); // idem

        userRepository.findByEmail(email).ifPresent(user -> {
            user.setConnected(false);
            userRepository.save(user);
        });
    }
}
