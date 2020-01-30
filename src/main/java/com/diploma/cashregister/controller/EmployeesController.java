package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.Contract;
import com.diploma.cashregister.domain.Role;
import com.diploma.cashregister.domain.Worker;
import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.service.EmployeeService;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeesController {
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public EmployeesController(EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("editPersonalData")
    public String editPersonalData(
                                 @RequestParam List<String> roles,
                                 @RequestParam("idWorker") Worker workerFromDb,
                                 @Valid Worker worker,
                                 @RequestParam("idPassword") WorkerPassword workerPasswordFromDb,
                                 @RequestParam("idContract") Contract currentContract,
                                 @RequestParam String birthday,
                                 @RequestParam String pass1,
                                 @RequestParam String start,
                                 @RequestParam String finish,
                                 @RequestParam String login,
                                Model model)
    {
            if (!currentContract.getDateStart().isEqual(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"))) || !currentContract.getDateEnd().isEqual(LocalDate.parse(finish, DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {
                currentContract.setDateEnd(LocalDate.now());
                Contract contract = new Contract();
                contract.setPosition(worker.getPosition());
                createContract(start, finish, workerFromDb, contract);
                workerFromDb.setPosition(worker.getPosition());
                employeeService.saveEmployeesNewContract(contract);
            }else {
                model.addAttribute("contractError", "Create contract for employee's position");
                fillModel(workerPasswordFromDb.getLogin(), roles, workerFromDb, birthday, start, finish, model);
                return "employee/addEmployee";
            }
        createWorker(roles, birthday, workerFromDb);
        workerFromDb.setContact(worker.getContact());
        workerFromDb.setName(worker.getName());
        workerFromDb.setSurname(worker.getSurname());
        if (!pass1.isEmpty()){
            workerPasswordFromDb.setPassword(passwordEncoder.encode(pass1));
            workerPasswordFromDb.setWorker(workerFromDb);
        }
        workerPasswordFromDb.setLogin(login);
        employeeService.saveEmployee(workerFromDb,currentContract,workerPasswordFromDb);

        return "redirect:/myShop";
    }

    @PostMapping("addEmployee")
    public String createEmployee(@RequestParam String login,
                                 @RequestParam List<String> roles,
                                 @Valid Worker worker,
                                 @RequestParam String birthday,
                                 @RequestParam String pass1,
                                 @RequestParam String start,
                                 @RequestParam String finish,
                                Model model)
    {
        WorkerPassword password = new WorkerPassword();
        if (employeeService.checkLogin(login)) password.setLogin(login);
        else {
            model.addAttribute("loginError", "Login already exist");
            fillModel(login, roles, worker, birthday, start, finish, model);
            return "employee/addEmployee";
        }
        Contract contract = new Contract();
        contract.setPosition(worker.getPosition());
            if (start.isEmpty() || finish.isEmpty()){
                model.addAttribute("contractError", "Create contract for employee's position");
                fillModel(login, roles, worker, birthday, start, finish, model);
                return "employee/addEmployee";
            }
        worker.setHireDate(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        createWorker(roles, birthday, worker);
        createPassword(pass1, worker, password);
        createContract(start, finish, worker, contract);

        employeeService.saveEmployee(worker,contract,password);
        return "redirect:/myShop";
    }

    private void fillModel( String login, List<String> roles, Worker worker, String birthday, String start, String finish, Model model) {
        model.addAttribute("positions", employeeService.getAllPositions());
        model.addAttribute("login",login );
        model.addAttribute("roles",roles );
        model.addAttribute("employee",worker );
        model.addAttribute("birthday",birthday );
        model.addAttribute("start",start );
        model.addAttribute("finish",finish );
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

    private void createContract( String start,  String finish, Worker worker, Contract contract) {
        contract.setDateStart(LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setDateEnd(LocalDate.parse(finish, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        contract.setWorker(worker);
    }

    private void createPassword(String pass, Worker worker, WorkerPassword password) {
        password.setPassword(passwordEncoder.encode(pass));
        password.setWorker(worker);
    }

    private void createWorker(List<String> roles, String birth, Worker worker) {
        worker.setDateOfBirthday(LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        worker.setRoles(new HashSet<>());
        roles.forEach(role-> worker.addRole(Role.valueOf(role)));
    }
}
