package com.groupeC.whatsapp.utils;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserLogUtils {
    public static String utilsConnecter(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
