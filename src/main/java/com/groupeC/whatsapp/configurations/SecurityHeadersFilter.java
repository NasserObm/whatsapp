package com.groupeC.whatsapp.configurations;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("X-Frame-Options", "DENY"); // Protège contre le clickjacking
        res.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains"); // Force HTTPS
        res.setHeader("X-Content-Type-Options", "nosniff"); // Empêche sniffing MIME
        res.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self'; object-src 'none'; base-uri 'self';"); // CSP stricte

        chain.doFilter(request, response);
    }
}
