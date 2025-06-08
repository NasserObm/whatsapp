package com.groupeC.whatsapp.securities;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//Classe de configuration gérer en auto par spring qui centralise les différentes données que seront utiliser par le JWTService
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Getter
public class JwtConfig {
    private String secret;
    private long expiration;

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
