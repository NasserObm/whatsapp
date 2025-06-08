package com.groupeC.whatsapp.models.dtos.requests;

public class MessageRequest {
    //Champ obligatoire pour l'envoie d'un message
    private String receiverEmail;
    private String content;

    //Constructeur
    public MessageRequest() {
    }

    //Getters et Setters
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
}
