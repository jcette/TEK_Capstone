package org.johnchoi.insuranceoptimizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    /**
     * Displays login page to user
     * @param modelAndView
     * @return
     */
    @RequestMapping(value="/login")
    public ModelAndView showLoginPage(ModelAndView modelAndView){

        modelAndView.setViewName("login");
        return modelAndView;
    }


}


