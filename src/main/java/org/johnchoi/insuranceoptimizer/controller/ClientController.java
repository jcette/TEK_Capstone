package org.johnchoi.insuranceoptimizer.controller;

import org.johnchoi.insuranceoptimizer.models.*;
import org.johnchoi.insuranceoptimizer.security.AppUserDetails;
import org.johnchoi.insuranceoptimizer.services.implementation.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<GroupPrediction> dummyGroupPrediction(String predictionType) {

        List<GroupPrediction> list = new ArrayList<>();

        GroupPrediction groupPrediction1 = new GroupPrediction();
        groupPrediction1.setAverageRisk(66.0);
        groupPrediction1.setDecrease(33.0);
        groupPrediction1.setMaintain(33.1);
        groupPrediction1.setIncrease(33.9);
        groupPrediction1.setType("Cancer");

        GroupPrediction groupPrediction2 = new GroupPrediction();
        groupPrediction2.setAverageRisk(56.0);
        groupPrediction2.setDecrease(23.0);
        groupPrediction2.setMaintain(43.1);
        groupPrediction2.setIncrease(33.9);
        groupPrediction2.setType("Heart");

        if(predictionType.equalsIgnoreCase("Cancer") || predictionType.equalsIgnoreCase("ALL")) {
            list.add(groupPrediction1);
        }

        if(predictionType.equalsIgnoreCase("Heart") || predictionType.equalsIgnoreCase("ALL")) {
            list.add(groupPrediction2);
        }

        return list;

    }

    private List<CancerData> dummyCancerData(){
        List<CancerData> list = new ArrayList<>();

        CancerData cancerData1 = new  CancerData();
        cancerData1.setPriorCancer(true);
        cancerData1.setFamilyCancerHistory(false);
        cancerData1.setAge(56);
        cancerData1.setDiet(Diet.UNHEALTHY);
        cancerData1.setHeight(72.0);
        cancerData1.setWeight(200.0);
        cancerData1.setExercise(Exercise.REGULAR);
        cancerData1.setSmoking(Smoking.MEDIUM);
        cancerData1.setSubstanceUse(SubstanceUse.LOW);
        cancerData1.setCancerPrediction(40.0F);
        cancerData1.setCancerRecommendation(Recommendation.randomRecommendation());

        CancerData cancerData2 = new  CancerData();
        cancerData2.setPriorCancer(false);
        cancerData2.setFamilyCancerHistory(true);
        cancerData2.setAge(46);
        cancerData2.setDiet(Diet.HEALTHY);
        cancerData2.setHeight(82.0);
        cancerData2.setWeight(180.0);
        cancerData2.setExercise(Exercise.HIGH);
        cancerData2.setSmoking(Smoking.NONE);
        cancerData2.setSubstanceUse(SubstanceUse.LOW);
        cancerData2.setCancerPrediction(60.0F);
        cancerData2.setCancerRecommendation(Recommendation.randomRecommendation());

        list.add(cancerData1);
        list.add(cancerData2);

        return list;
    }


    // the set recommendations that come later will override the previous ones...
    private List<HeartData> dummyHeartData(){
        List<HeartData> list = new ArrayList<>();

        HeartData heartData1 = new HeartData();
        heartData1.setAge(66);
        heartData1.setDiet(Diet.UNHEALTHY);
        heartData1.setHeight(72.0);
        heartData1.setWeight(200.0);
        heartData1.setExercise(Exercise.REGULAR);
        heartData1.setSmoking(Smoking.MEDIUM);
        heartData1.setSubstanceUse(SubstanceUse.LOW);
        heartData1.setBloodPressure(150.0);
        heartData1.setCholesterol(260.0);
        heartData1.setPriorHeartDisease(true);
        heartData1.setFamilyHeartDiseaseHistory(false);
        heartData1.setHeartPrediction(30.0F);
        heartData1.setHeartRecommendation(Recommendation.randomRecommendation());

        HeartData heartData2 = new HeartData();
        heartData2.setAge(64);
        heartData2.setDiet(Diet.HEALTHY);
        heartData2.setHeight(82.0);
        heartData2.setWeight(180.0);
        heartData2.setExercise(Exercise.HIGH);
        heartData2.setSmoking(Smoking.NONE);
        heartData2.setSubstanceUse(SubstanceUse.LOW);
        heartData2.setBloodPressure(130.0);
        heartData2.setCholesterol(200.0);
        heartData2.setPriorHeartDisease(false);
        heartData2.setFamilyHeartDiseaseHistory(true);
        heartData2.setHeartPrediction(30.0F);
        heartData2.setHeartRecommendation(Recommendation.randomRecommendation());

        list.add(heartData1);
        list.add(heartData2);

        return list;
    }


}
