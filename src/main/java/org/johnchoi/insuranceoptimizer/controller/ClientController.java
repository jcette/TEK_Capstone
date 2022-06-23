package org.johnchoi.insuranceoptimizer.controller;

import org.johnchoi.insuranceoptimizer.models.*;
import org.johnchoi.insuranceoptimizer.models.constant.*;
import org.johnchoi.insuranceoptimizer.security.AppUserDetails;
import org.johnchoi.insuranceoptimizer.services.implementation.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


//To-do: filter client view so that they only see their own data

@Controller
public class ClientController {
    @Autowired
private AdminService adminService;
    @GetMapping("/client")
    public ModelAndView getClientPage(ModelAndView modelAndView, ControlPanelForm controlPanelForm) {
        modelAndView.setViewName("/client/client");
        modelAndView.addObject("controlForm", controlPanelForm);
        return modelAndView;
    }

    @PostMapping("/clientcontrol")
    public ModelAndView processClientSelection(ModelAndView modelAndView, @Validated ControlPanelForm controlPanelForm) {


        modelAndView.setViewName("/client/client");
        modelAndView.addObject("controlForm", controlPanelForm);

        String prediction = controlPanelForm.getPrediction();
        String population = controlPanelForm.getPopulation();

        // Get the current logged In client details from Spring security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails appUserDetails = (AppUserDetails) auth.getPrincipal();
        String clientEmail  =  appUserDetails.getUser().getEmail();

        // Get the client data
        ClientData clientData = this.adminService.getClientData(clientEmail );
        List<CancerData> cancerData = null;
        List<HeartData> heartData = null;
        List<GroupPrediction> groupPrediction = null;

        switch (prediction){
            case "Cancer":
                if(population.equalsIgnoreCase("Individual") || population.equalsIgnoreCase("Both")){
                    cancerData =clientData.getCancerData();
                }
                if(population.equalsIgnoreCase("Group") || population.equalsIgnoreCase("Both")){
                    groupPrediction = clientData.getGroupPredictions().stream().filter( gp->gp.getType().equalsIgnoreCase( "Cancer" ) ).collect( Collectors.toList());
                }
                break;
            case "Heart":
                if(population.equalsIgnoreCase("Individual") || population.equalsIgnoreCase("Both")) {
                    heartData = clientData.getHeartData();
                }
                if(population.equalsIgnoreCase("Group") || population.equalsIgnoreCase("Both")) {
                    groupPrediction =  groupPrediction = clientData.getGroupPredictions().stream().filter( gp->gp.getType().equalsIgnoreCase( "Heart" ) ).collect( Collectors.toList());
                }
                break;
            case "ALL":
                if(population.equalsIgnoreCase("Individual") || population.equalsIgnoreCase("Both")) {
                    cancerData = clientData.getCancerData();
                    heartData = clientData.getHeartData();
                }
                if(population.equalsIgnoreCase("Group") || population.equalsIgnoreCase("Both")){
                    groupPrediction = clientData.getGroupPredictions();
                }
                break;
            default:
                String message = "There was a problem";
                modelAndView.addObject("errormessage", message);
                break;
        }

        modelAndView.addObject("heartData", heartData);
        modelAndView.addObject("cancerData", cancerData);
        modelAndView.addObject("groupPrediction", groupPrediction);
        return modelAndView;

    }


}
