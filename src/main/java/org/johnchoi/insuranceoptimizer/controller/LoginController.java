package org.johnchoi.insuranceoptimizer.controller;

import org.johnchoi.insuranceoptimizer.models.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

//    @Autowired
//    public LoginController(LoginService loginService){
//        this.loginService = loginService;
//    }
//
//    private LoginService loginService;

    @RequestMapping(value="/login")
    public ModelAndView showLoginPage(ModelAndView modelAndView, LoginRequest user){

        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView processLogin(ModelAndView modelAndView, LoginRequest loginRequest){
//
//        try {
//            User user = loginService.processLogin(loginRequest);
//            UserRoles role = user.getUserRole();
//
//            if(role==UserRoles.ADMIN){
//                modelAndView.setViewName("Admin/admin");
//            } else if(role==UserRoles.CLIENT) {
//                modelAndView.setViewName("Client/client");
//            } else {
//                modelAndView.setViewName("login");
//            }
//        } catch (Exception e) {
//            modelAndView.addObject("errorMessage", e.getMessage());
//            modelAndView.setViewName("login");
//        }
//
//
//        return modelAndView;
//    }

}


