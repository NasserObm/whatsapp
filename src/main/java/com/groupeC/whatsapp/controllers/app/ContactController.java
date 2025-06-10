package com.groupeC.whatsapp.controllers.app;

import com.groupeC.whatsapp.models.dtos.requests.AddContactRequest;
import com.groupeC.whatsapp.models.dtos.responses.ContactResponse;
import com.groupeC.whatsapp.securities.JwtService;
import com.groupeC.whatsapp.services.interfaces.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final JwtService jwtService;   // pour extraire l’email depuis le token
/*
    // ➜ Ajouter un contact
    @PostMapping("/add")
    public ResponseEntity<?> addContact(
            @RequestBody AddContactRequest request) {
        contactService.addContact(request.getContactEmail());
        return ResponseEntity.ok("Contact ajouté !");
    }
*/
    // ➜ Récupérer la liste des contacts + statut en ligne
    @GetMapping
    public ResponseEntity<List<ContactResponse>> listContacts() {
        return ResponseEntity.ok(contactService.listContacts());
    }
}
