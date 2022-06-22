package org.johnchoi.insuranceoptimizer.controller;

import org.johnchoi.insuranceoptimizer.models.User;
import org.johnchoi.insuranceoptimizer.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistration(ModelAndView modelAndView, @Validated User user, BindingResult bindingResult){

        try {
            if(bindingResult.hasErrors()){
                modelAndView.addObject("message", "Please fill up all the details");
                modelAndView.setViewName("registration");
                return modelAndView;
            }
            String output = this.registrationService.registerUser(user);
            //check if user already logged in and is Admin
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            modelAndView.addObject("message", output);
            if(auth.isAuthenticated() && auth.getAuthorities().contains( new SimpleGrantedAuthority( "ADMIN"))){
                return new ModelAndView("redirect:/admin");
            }else{
                modelAndView.setViewName("login");
            }

        }catch (Exception e){
            modelAndView.addObject("message", "Failed to Register the user");
            modelAndView.setViewName("registration");
        }


        return modelAndView;
    }
}
