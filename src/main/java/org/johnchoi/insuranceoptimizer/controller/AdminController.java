package org.johnchoi.insuranceoptimizer.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.johnchoi.insuranceoptimizer.exceptions.UserNotFoundException;
import org.johnchoi.insuranceoptimizer.models.*;
import org.johnchoi.insuranceoptimizer.services.implementation.AdminService;
import org.johnchoi.insuranceoptimizer.services.implementation.UserService;
import org.johnchoi.insuranceoptimizer.util.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private UserService userService;
    private AdminService adminService;

    @Autowired
    public AdminController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    /**    Creates a clients object to populate dropdown menu, and an adminForm object to take in the user input (client selection, show data checkbox, and csv file)
     *
     */
    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView modelAndView, AdminForm adminForm) {

        // retrieve all the user having role as Client.
        List<User> clients = userService.getClients();
        modelAndView.addObject("clients", clients);
        modelAndView.addObject("adminForm", adminForm);
        modelAndView.setViewName("/admin/admin");
        return modelAndView;
    }
    /** if admin elects to update someone, redirects to update-data html and injects dataId to show which row you are updating
     *
     */
    @PostMapping("/update-form")
    public String showUpdateForm(@RequestParam(value = "dataId", required = true) Long dataId, Model model){
        ClientDataForm clientDataForm = this.adminService.getClientDataById(dataId);
        clientDataForm.setDataId( dataId );
        model.addAttribute("clientDataForm", clientDataForm);
        model.addAttribute( "dataId",  dataId);
        return "admin/update-data";
    }

    /** admin functionality to edit a particular row of backend
     *
     * @param form
     * @param model
     * @return
     */
    @PostMapping("/update")
    public String updateData(ClientDataForm form,  Model model){
        try{
            this.adminService.updateRecord(form);
            model.addAttribute("message", "Record updated successfully");
        }catch (Exception ex){
            ex.printStackTrace();
            model.addAttribute("message", "Error in update call");
        }
        return "success";
    }

    /** admin functionality to delete a particular row of backend.
     *
     * @param dataId
     * @param model
     * @return
     */
    @PostMapping("/delete")
    public String delete(@RequestParam(value = "dataId", required = true) Long dataId, Model model){
        this.adminService.deleteDataById(dataId);
        model.addAttribute( "message", "Record deleted successfully" );
        return "success";
    }

    /** Saves the user input -- populates backend with csv file linked to the specific client. Also allows you to submit additional csv or make adminform selection changes without actually refreshing page
     *
     * @param file
     * @param model
     * @param adminForm
     * @return
     */
    @PostMapping("/admin-control")
                public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model, AdminForm adminForm) {
        ClientData clientData;
        if (adminForm.isShowData()) {
            //show data
            clientData = getClientData( adminForm.getClientEmail( ) );
        } else {
            clientData = uploadClientData( file, adminForm.getClientEmail( ) );
            model.addAttribute( "message", file.isEmpty() ? "Please select a CSV file to upload." :
                                           clientData!=null ? "File uploaded Successfully" :
                                           "Error in uploading the file" );
            model.addAttribute( "status",  clientData!=null ? true:false );
        }
        // This is to be able to upload additional data or select a new client to upload data, on the same page.
        List<User> clients = userService.getClients();
        model.addAttribute("clients", clients);
        model.addAttribute( "clientData", clientData );
        return "/admin/admin";
    }

    /** method to read the CSV and transfer to clientdata object to eventually populate backend database
     *
     * @param file
     * @param clientEmail
     * @return
     */
    private ClientData uploadClientData(MultipartFile file, String clientEmail) {
        ClientData clientData = null;
        try {
            List<HealthCSV> healthCSVs = CSVReader.readCSV( file );
            clientData =  adminService.saveCsvData(healthCSVs, clientEmail);
        } catch (Exception | UserNotFoundException ex) {
            ex.printStackTrace();
        }

        return clientData;
    }

    /** method to retrieve client data from backend
     *
     * @param clientEmail
     * @return
     */
    private ClientData getClientData(String clientEmail) {

       ClientData clientData = this.adminService.getClientData(clientEmail);
        return clientData;
    }
}
