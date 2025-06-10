package com.groupeC.whatsapp.services.implementations;

import com.groupeC.whatsapp.exceptions.ApiException;
import com.groupeC.whatsapp.models.dtos.responses.ContactResponse;
import com.groupeC.whatsapp.models.entities.User;
import com.groupeC.whatsapp.models.mappers.UserMapper;
import com.groupeC.whatsapp.repositories.UserRepository;
import com.groupeC.whatsapp.services.interfaces.ContactService;
import com.groupeC.whatsapp.utils.UserLogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final UserRepository userRepository;
    private final ConnectedUserService connectedUserService;
    private final UserMapper contactMapper;

   /* @Override
    public void addContact( String contactEmail) {
        String ownerEmail= UserLogUtils.utilsConnecter();//récupération de l'email
        //Verification de l'existence du mail en db
        User user= userRepository.findByEmail(ownerEmail).orElseThrow(
                ()->new ApiException("Utilisateur non reconnu")
        );
        if (ownerEmail.equals(contactEmail)) throw new IllegalArgumentException("On ne peut pas s’ajouter soi-même");

        User owner   = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner introuvable"));
        User contact = userRepository.findByEmail(contactEmail)
                .orElseThrow(() -> new RuntimeException("Contact introuvable"));

        owner.getContacts().add(contact);
        userRepository.save(owner);     // côté inverse inutile grâce à la table de jointure
    }
*/
    @Override
    public List<ContactResponse> listContacts() {
        String ownerEmail= UserLogUtils.utilsConnecter();//récupération de l'email
        //Verification de l'existence du mail en db
        User owner= userRepository.findByEmail(ownerEmail).orElseThrow(
                ()->new ApiException("Utilisateur non reconnu")
        );
        return owner.getContacts().stream()
                .map(c -> contactMapper.toDtos(
                        c,
                        connectedUserService.isUserConnected(c.getEmail())))
                .toList();
    }
}
