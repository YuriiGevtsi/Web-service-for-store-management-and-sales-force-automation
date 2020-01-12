package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.Contract;
import com.diploma.cashregister.domain.Role;
import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.service.EmployeeService;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeesController {
    @Autowired
    private final EmployeeService employeeService;

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("addEmployee")
    public String addEmployee(Model model){
        model.addAttribute("positions", employeeService.getAllPositions());
        return "employee/addEmployee";
    }
    @GetMapping("allEmployees")
    public String allEmployees(Model model){
        model.addAttribute("employees",employeeService.getAllEmployees());
        return "employee/allEmployees";
    }

    @GetMapping("contracts")
    public String contracts(Model model){
        model.addAttribute("contracts", employeeService.getAllContracts());
        return "employee/contracts";
    }
    @GetMapping("suppliers")
    public String suppliers(Model model){
        model.addAttribute("providers", employeeService.getAllProviders());
        return "suppliers/providers";
    }
    @GetMapping("editPersonalData")
    public String editPersonalData(@RequestParam long idWorker, Model model){
        List<String> roles = new ArrayList<>();
        Worker employee = employeeService.getEmployee(idWorker);
        employee.getRoles().forEach(role -> roles.add(role.name()));

        model.addAttribute("employee", employee);
        model.addAttribute("password", employeeService.getEmployeesPassword(employee));
        model.addAttribute("roles", roles);
        model.addAttribute("positions", employeeService.getAllPositions());
        model.addAttribute("currentContract", employeeService.getCurrentContruct(employee));
        return "employee/addEmployee";
    }
    @PostMapping("addEmployee")
    public String createEmployee(@RequestParam String login,
                                 @RequestParam Long position,
                                 @RequestParam List<String> roles,
                                 @RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String birth,
                                 @RequestParam String pass1,
                                 @RequestParam String start,
                                 @RequestParam String finish,
                                 @RequestParam String contact,
                                 @RequestParam(required = false, defaultValue = "-1") Long idWorker)
    {
        Worker worker;
        WorkerPassword password;
        Contract contract;
        if (idWorker!=-1){
            worker = employeeService.getEmployee(idWorker);
            password = employeeService.getEmployeesPassword(worker);
            contract = employeeService.getCurrentContruct(worker);
            if (!contract.getDateStart().isEqual(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"))) ||
                    !contract.getDateEnd().isEqual(LocalDate.parse(finish, DateTimeFormatter.ofPattern("yyyy-MM-dd"))) )contract = new Contract();
        }
        else{
            worker = new Worker();
            password = new WorkerPassword();
            contract = new Contract();
            worker.setHireDate(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        worker.setDateOfBirthday(LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        worker.setName(firstName);
        worker.setSurname(lastName);
        worker.setContact(contact);
        worker.setRoles(new HashSet<>());
        roles.forEach(role-> worker.addRole(Role.valueOf(role)));


        password.setPassword(pass1);
        password.setLogin(login);
        password.setWorker(worker);


        contract.setDateStart(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setDateEnd(LocalDate.parse(finish, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setWorker(worker);

        employeeService.createEmployee(worker,password,contract,position);

        return "redirect:/myShop";
    }

    @PostMapping("deleteEmployee")
    @ResponseBody
    public  String deleteEmployee(@RequestBody String id){
        JSONObject jsonObject = (JSONObject) JSONValue.parse(id);
        employeeService.deleteEmployee(Long.valueOf((String) jsonObject.get("id")));
        return "200";
    }
    @PostMapping("deleteContract")
    @ResponseBody
    public  String deleteContract(@RequestBody String id){
        JSONObject jsonObject = (JSONObject) JSONValue.parse(id);
        employeeService.deleteContract(Long.valueOf((String) jsonObject.get("id")));
        return "200";
    }
    @PostMapping("createProvider")
    @ResponseBody
    public  String createProvider(@RequestBody String provider){
        JSONObject jsonObject = (JSONObject) JSONValue.parse(provider);
        employeeService.createProvider(jsonObject.get("name").toString(),jsonObject.get("email").toString(),jsonObject.get("address").toString());
        return "200";
    }
    @PostMapping("editProvider")
    @ResponseBody
    public  String editProvider(@RequestBody String provider){
        JSONObject jsonObject = (JSONObject) JSONValue.parse(provider);
        employeeService.editProvider(jsonObject.get("idProvider").toString(),jsonObject.get("name").toString(),jsonObject.get("email").toString(),jsonObject.get("address").toString());
        return "200";
    }
    @PostMapping("deleteProvider")
    @ResponseBody
    public  String deleteProvider(@RequestBody String id){
        JSONObject jsonObject = (JSONObject) JSONValue.parse(id);
        employeeService.deleteProvider( Long.parseLong(jsonObject.get("id").toString()));
        return "200";
    }

}
