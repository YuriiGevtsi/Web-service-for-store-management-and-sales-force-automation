package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.*;
import com.diploma.cashregister.service.ProductService;
import com.diploma.cashregister.service.SellingOperationService;
import com.diploma.cashregister.service.WorkerPasswordService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Controller
public class MenuController {
    @Autowired
    private final WorkerPasswordService workerPasswordService;
    @Autowired
    private final SellingOperationService sellingOperationService;
    @Autowired
    private final ProductService productService ;

    public MenuController(SellingOperationService sellingOperationService, ProductService productService,
                          WorkerPasswordService workerPasswordService) {
        this.sellingOperationService = sellingOperationService;
        this.productService = productService;
        this.workerPasswordService = workerPasswordService;
    }

    @GetMapping("/")
    public String greeting(@AuthenticationPrincipal WorkerPassword workerPassword, Model model) {
        Shift shift = workerPasswordService.getCurrentShift();
        if (shift == null) model.addAttribute("shift",false);
        else model.addAttribute("shift",true);

        ShiftWorker shiftWorker = workerPasswordService.getCurrentShiftWorker(workerPassword.getWorker()).orElse(null);
        if (shiftWorker == null  ) {
            model.addAttribute("shiftWorker",false);
            if (shift != null)workerPasswordService.createShiftWorker(workerPassword.getWorker(),shift );
        }else model.addAttribute("shiftWorker",true);

        model.addAttribute("worker",workerPassword.getWorker());
        return "mainMenu/main_menu";
    }
    @PostMapping("/")
    public String startShift(@AuthenticationPrincipal WorkerPassword workerPassword) {
        workerPasswordService.createShift(workerPassword.getWorker());
        return "redirect:/";
    }
    @PostMapping("/noShift")
    public String noShift(@AuthenticationPrincipal WorkerPassword workerPassword) {
        workerPasswordService.createShiftWorker(workerPassword.getWorker(),null );
        return "redirect:/";
    }

    @PostMapping("cash")
    public @ResponseBody String cashOut(
            @RequestParam float amount,
            @RequestParam String comment,
            @RequestParam String type,
            @AuthenticationPrincipal WorkerPassword workerPassword
    ){
        ShiftWorker shiftWorker = workerPassword.getWorker().getShiftWorkers().stream().filter(e -> e.getLogoutTime() == null).findAny().orElse(null);

        FinancialOperations financialOperations = new FinancialOperations();
        financialOperations.setComment(comment.isEmpty()? workerPassword.getWorker().getName() : comment );
        financialOperations.setSumm(amount);
        financialOperations.setTime(LocalDateTime.now());
        financialOperations.setType(type.equalsIgnoreCase("in")? "Cash acceptance" : "Cash withdrawal");
        if (shiftWorker != null){
            financialOperations.setShift(shiftWorker.getShift());
        }

        sellingOperationService.saveFinancialOperation(financialOperations);
        return "ok";
    }

    @GetMapping("return")
    public String returnProduct(@RequestParam long number, Model model){
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
    public @ResponseBody String finishWork(@AuthenticationPrincipal WorkerPassword workerPassword, @RequestParam String param){
        workerPasswordService.closeShift(workerPassword.getWorker());

        return param;
    }

    @GetMapping("find-product")
    public @ResponseBody String findProduct(@RequestParam(required = true) String code){
        ProviderProduct productByBarcode = productService.findProductByBarcode(code);
        String name = productByBarcode == null ? "error" : productByBarcode.getName();
        return name;
    }

    @PostMapping(value = "/write-off")
    public String writeOff(@RequestParam(required = true) String barcode,
                           @RequestParam(required = true) double amount,
                           @RequestParam(required = false, defaultValue = "") String reason
    ){
        WrittenOffProduct product = new WrittenOffProduct();
        product.setAmount(amount);
        product.setDate(LocalDate.now());
        product.setReason(reason);
        product.setProviderProduct(productService.findProductByBarcode(barcode));

        productService.saveWrittenOffProduct(product);
        return "redirect:/";
    }

    @GetMapping("exit")
    public @ResponseBody String logOut(@AuthenticationPrincipal WorkerPassword workerPassword){
        workerPasswordService.logOutWorker(workerPassword.getWorker());
        return "ok";
    }
    @GetMapping("myShop")
    public String myShop(){
        return "mainMenu/myShop";
    }

}
