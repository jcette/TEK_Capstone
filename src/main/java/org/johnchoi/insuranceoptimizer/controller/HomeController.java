package org.johnchoi.insuranceoptimizer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    /**
     * When you first go to the homepage, you see the index.html.
     * @return
     */
    @GetMapping("/home")
    public String home() {
        return "/index";
    }

//    @GetMapping("/client")
//    public String user() {
//        return "/client/client";
//    }

    /**
     * redirect to login page
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    /**
     * gives a 403 error if there is an error in the application (user not authenticated, unauthorized, etc)
     * @return
     */
    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    /**
     * If user elects to logout, clears user session
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
