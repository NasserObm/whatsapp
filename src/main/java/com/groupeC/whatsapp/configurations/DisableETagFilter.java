package com.groupeC.whatsapp.configurations;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DisableETagFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;

        chain.doFilter(request, response);
        res.setHeader("ETag", null); // Supprime l'en-tête ETag
    }
}
