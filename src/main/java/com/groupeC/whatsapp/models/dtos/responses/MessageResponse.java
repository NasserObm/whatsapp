package com.groupeC.whatsapp.models.dtos.responses;

import java.time.LocalDateTime;

public class MessageResponse {
    private String senderEmail;
    private String receiverEmail;
    private String content;
    private boolean seen;
    private LocalDateTime timestamp;

    // Constructeurs
    public MessageResponse() {}

    public MessageResponse(String senderEmail, String receiverEmail, String content, LocalDateTime timestamp,boolean seen) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.content = content;
        this.seen=seen;
        this.timestamp = timestamp;
    }

    // Getters et Setters

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
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
