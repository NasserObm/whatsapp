package com.groupeC.whatsapp.models.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
//Classe pour l'entité des messages
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//Id unique du message

    private String senderEmail;//email de la personne qui envoie le message
    private String receiverEmail;//email de la personne qui reçoit le message
    private String content;//contenu du message
    private boolean seen;//message vue ou pas
    private LocalDateTime timestamp;//heure de l'envoie du message


    // Constructeurs,

    public Message(Long id, String senderEmail, String receiverEmail, String content, boolean seen, LocalDateTime timestamp) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.seen = seen;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    //getters et  setters

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
