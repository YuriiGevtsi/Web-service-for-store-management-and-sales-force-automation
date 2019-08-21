package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.FinancialOperations;
import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.repos.FinancialOperationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class MenuController {
    @Autowired
    private final FinancialOperationRepo financialOperationRepo;

    public MenuController(FinancialOperationRepo financialOperationRepo) {
        this.financialOperationRepo = financialOperationRepo;
    }

    @GetMapping("/")
    public String greeting(Model model) {return "mainMenu/main_menu";}

    @GetMapping("cash_in")
    public String cashIn(Model model){
        model.addAttribute("type","in");
        return "mainMenu/cashInOut";
    }

    @PostMapping("cash_in")
    public String cashIn(
            @RequestParam(required = true) float sum,
            @RequestParam(required = false, defaultValue = "Вплата") String comment,
            Model model,
            @AuthenticationPrincipal WorkerPassword workerPassword
    ){
        FinancialOperations financialOperations = new FinancialOperations();
        financialOperations.setComment(comment);
        financialOperations.setSumm(sum);
        financialOperations.setTime(LocalDateTime.now());
        financialOperations.setType("Вплата");

        financialOperationRepo.save(financialOperations);
        return "redirect:/";
    }
    @PostMapping("cash_out")
    public String cashOut(
            @RequestParam(required = true) float sum,
            @RequestParam(required = false, defaultValue = "Выплата") String comment,
            Model model,
            @AuthenticationPrincipal WorkerPassword workerPassword
    ){
        FinancialOperations financialOperations = new FinancialOperations();
        financialOperations.setComment(comment +" " + workerPassword.getWorker().getName());
        financialOperations.setSumm(sum);
        financialOperations.setTime(LocalDateTime.now());
        financialOperations.setType("Выплата");

        financialOperationRepo.save(financialOperations);
        return "ok";
    }

    @GetMapping("cash_out")
    public String cashOut(Model model){
        model.addAttribute("type","out");
        return "mainMenu/cashInOut";
    }

}
