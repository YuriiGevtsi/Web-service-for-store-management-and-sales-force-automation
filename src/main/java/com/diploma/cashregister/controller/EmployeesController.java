package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.Contract;
import com.diploma.cashregister.domain.Role;
import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

        return "employee/contracts";
    }
    @GetMapping("editPersonalData")
    public String editPersonalData(@RequestParam long idWorker, Model model){
        List<String> roles = new ArrayList<>();
        employeeService.getEmployee(idWorker).getRoles().forEach(role -> roles.add(role.name()));
        model.addAttribute("employee", employeeService.getEmployee(idWorker));
        model.addAttribute("password", employeeService.getEmployeesPassword(employeeService.getEmployee(idWorker)));
        model.addAttribute("roles", roles);
        model.addAttribute("positions", employeeService.getAllPositions());
        return "employee/addEmployee";
    }
    @PostMapping("addEmployee")
    public String createEmployee(@RequestParam String login,
                                 @RequestParam Long position,
                                 @RequestParam String role,
                                 @RequestParam String firstName,
                                 @RequestParam String lastName,
                                 @RequestParam String birth,
                                 @RequestParam String pass1,
                                 @RequestParam String start,
                                 @RequestParam String finish,
                                 @RequestParam String contact)
    {
        Worker worker = new Worker();
        worker.setDateOfBirthday(LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        worker.setName(firstName);
        worker.setSurname(lastName);
        worker.setContact(contact);
        worker.setRoles(Role.valueOf(role));


        WorkerPassword password = new WorkerPassword();
        password.setPassword(pass1);
        password.setLogin(login);
        password.setWorker(worker);

        Contract contract = new Contract();
        contract.setDateStart(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setDateEnd(LocalDate.parse(finish, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setWorker(worker);


        employeeService.createEmployee(worker,password,contract,position);

        return "redirect:/myShop";
    }
}
