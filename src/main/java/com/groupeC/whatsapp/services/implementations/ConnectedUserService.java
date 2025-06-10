package com.groupeC.whatsapp.services.implementations;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConnectedUserService {
    private final Set<String> connectedUsers = ConcurrentHashMap.newKeySet();

    public void userConnected(String email) {
        connectedUsers.add(email);
    }

    public void userDisconnected(String email) {
        connectedUsers.remove(email);
    }

    public boolean isUserConnected(String email) {
        return connectedUsers.contains(email);
    }

    public Set<String> getConnectedUsers() {
        return connectedUsers;
    }
}
