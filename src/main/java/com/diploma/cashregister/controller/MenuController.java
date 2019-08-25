package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.Bucket;
import com.diploma.cashregister.domain.FinancialOperations;
import com.diploma.cashregister.domain.Price;
import com.diploma.cashregister.domain.WorkerPassword;
import com.diploma.cashregister.repos.FinancialOperationRepo;
import com.diploma.cashregister.repos.SellingOperationRepo;
import com.diploma.cashregister.service.SellingOperationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Controller
public class MenuController {

    @Autowired
    private final SellingOperationService sellingOperationService;

    public MenuController(SellingOperationService sellingOperationService) {
        this.sellingOperationService = sellingOperationService;
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

        sellingOperationService.saveFinancialOperation(financialOperations);
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


        sellingOperationService.saveFinancialOperation(financialOperations);
        return "ok";
    }

    @GetMapping("cash_out")
    public String cashOut(Model model){
        model.addAttribute("type","out");
        return "mainMenu/cashInOut";
    }


    @GetMapping("return")
    public String returnProduct(@RequestParam(required = true) long number, Model model){
        if (sellingOperationService.findReceipt(number)){
            model.addAttribute("receipt","Неверный номер чека");
            return "mainMenu/main_menu";
        }
        Set<Bucket> products = sellingOperationService.getReceiptProducts(number);
        model.addAttribute("products", products);
        model.addAttribute("number", number);

        return "mainMenu/returnProd";
    }

    @PostMapping(value = "/return")
    public @ResponseBody String saveDelivery(@RequestBody String json) throws IOException {

        ObjectMapper jsonMapper = new ObjectMapper();
        TypeReference<Map<String, Map<String, String>>> typeRef =
                new TypeReference<Map<String, Map<String, String>>>() {};
        Map<String, Map<String, String>> map = jsonMapper.readValue(json, typeRef);

        sellingOperationService.returnProduct(map);

        return json;
    }


    @GetMapping("finish")
    public @ResponseBody String finishWork(@RequestParam(required = true) String param){
        System.out.println(param);

        return param;
    }
}
