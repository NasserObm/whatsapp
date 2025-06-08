package com.groupeC.whatsapp.models.entities;

import com.groupeC.whatsapp.models.enums.UserRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    //variables d'un utilisateur
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//Généreation automatique de l'id unique de l'utilisateur dans la base de donnée
    @Column(nullable = false,unique = true)
    private String email;//email unique pour chaque utilisateur
    @Column(nullable = false)
    private String name;//Nom de l'utilisateur
    @Column(nullable = false)
    private String password;//mot de passe qui sera bien sur sécurisé dans authentifcationServiceImpl

    //Enumeration de l'utilisateur
    @Enumerated(EnumType.STRING)
    private UserRole userRole;//indique le role de l'utilisateur

    //Implémentation des constructeurs
    public User(Long id, String email, String name, String password, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }

    public User() {
    }

    //implementation des getters et des setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Implementation du UserDetail
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // Pas encore de rôles/permissions
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Spring Security utilise ça comme identifiant
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // true = compte jamais expiré
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // true = compte jamais bloqué
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // true = mot de passe jamais expiré
    }

    @Override
    public boolean isEnabled() {
        return true; // true = compte actif
    }

    //Implémentation des fonction ou methode statique
}
