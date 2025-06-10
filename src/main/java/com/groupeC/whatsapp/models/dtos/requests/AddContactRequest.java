package com.groupeC.whatsapp.models.dtos.requests;

// Pour ajouter un contact
public class AddContactRequest {
    private String contactEmail;

    public AddContactRequest() {
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}

