package com.groupeC.whatsapp.repositories;

import com.groupeC.whatsapp.models.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

//Inteface pour la création et la gestion de la table de message
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderEmail = :email1 AND m.receiverEmail = :email2) OR " +
            "(m.senderEmail = :email2 AND m.receiverEmail = :email1) " +
            "ORDER BY m.timestamp ASC")
    List<Message> findConversation(String email1, String email2);

    List<Message> findByReceiverEmailAndSeenFalse(String email);
}
