package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.Order;
import com.diploma.cashregister.domain.OrderPayments;
import com.diploma.cashregister.service.DeliveryService;
import com.diploma.cashregister.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class DeliveryController {
    @Autowired
    private final DeliveryService deliveryService;
    @Autowired
    private final ProductService productService;

    public DeliveryController(DeliveryService deliveryService, ProductService productService) {
        this.deliveryService = deliveryService;
        this.productService = productService;
    }

    @GetMapping("delivery")
    public String delivery(Model model){
        return "delivery/delivery";
    }

    @GetMapping("findOrder")
    public @ResponseBody String findOrder(@RequestParam long number){
        if (deliveryService.getOrder(number) != null && !deliveryService.getOrder(number).getStatus().equals("accepted"))
            return "ok";
        else return "No order";
    }

    @GetMapping("/delivery_number")
    public String getDelivery(@RequestParam long number,@RequestParam String action,
                              Model model
    ) {
        Order order = deliveryService.getOrder(number);
        if (order != null && !order.getStatus().equals("accepted")){

            Set<OrderPayments> orderPayments = deliveryService.getOrderPayments(number);

            orderPayments.stream().forEach(payment ->{
                if (payment.getType().equalsIgnoreCase("pre")) model.addAttribute("pre", payment.getSum());
                else if (payment.getType().equalsIgnoreCase("onDelivery")) model.addAttribute("post", payment.getSum());
            });
            model.addAttribute("order", order);
            model.addAttribute("bucket",deliveryService.getProductFromOrderBucket(number));
            if (action.equals("edit")){
                model.addAttribute("supplier",order.getProvider().getIdProvider());
                model.addAttribute("products",productService.getAllProductsByProvider(order.getProvider().getIdProvider()));
                model.addAttribute("suppliers",deliveryService.getProviders());
                return "delivery/createOrder";
            }else return "delivery/delivery";
        }else {
            return "redirect:/";
        }
    }

    @GetMapping("/createOrder")
    public String getDelivery(Model model,@RequestParam(required = false,defaultValue = "-1") long provider ) {
        if (provider != -1){
            model.addAttribute("supplier",provider);
            model.addAttribute("products",productService.getAllProductsByProvider(provider));
        }
        model.addAttribute("suppliers",deliveryService.getProviders());
        return "delivery/createOrder";
    }

    @PostMapping(value = "/delivery_number")
    public @ResponseBody String saveDelivery(
            @RequestBody String json
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String,String>>(){});
        deliveryService.createDelivery(map);
        return json;
    }
    @PostMapping(value = "/createOrder")
    public @ResponseBody String saveOrder(
            @RequestBody String json
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String,String>>(){});
        try {
            deliveryService.createOrder(map);
            return json;
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }
    @PostMapping(value = "/updateOrder")
    public @ResponseBody String updateOrder(
            @RequestBody String json
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String,String>>(){});
        try {
            deliveryService.updateOrder(map);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }
}
