package com.groupeC.whatsapp.services.interfaces;

import com.groupeC.whatsapp.models.dtos.responses.ContactResponse;

import java.util.List;

public interface UserService {
 List<ContactResponse> listerContact();
}
