package org.johnchoi.insuranceoptimizer.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthSuccessHandler.class);

    private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
    private final GrantedAuthority clientAuthority = new SimpleGrantedAuthority("CLIENT");
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("User '" + authentication.getName()
                + "' attempted to access the protected URL: "
                + request.getRequestURI() + authentication.getAuthorities());
        if(checkRole(authentication, adminAuthority)){
            logger.info("Redirecting" +request.getContextPath() + "/admin");
            response.sendRedirect(request.getContextPath() + "/admin");
        }
        if(checkRole(authentication, clientAuthority)){
            logger.info("Redirecting" +request.getContextPath() + "/client");
            response.sendRedirect(request.getContextPath() + "/client");
        }
    }

    protected boolean checkRole(final Authentication authentication,  GrantedAuthority authority  )
    {
        return authentication!=null && authentication.getAuthorities()!=null && ! authentication.getAuthorities().isEmpty()
                && authentication.getAuthorities().contains(authority);
    }

}
