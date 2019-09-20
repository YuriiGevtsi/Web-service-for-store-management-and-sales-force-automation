package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.ProviderProduct;
import com.diploma.cashregister.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class MyShopController {
    @Autowired
    private final ProductService productService;
    @Value("${upload.path}")
    private String uploadPath;

    public MyShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("addProduct")
    public String addProduct(Model model){
        model.addAttribute("categories",productService.getCategories());
        model.addAttribute("manufacturers",productService.getManufacturers());
        model.addAttribute("measuringRates",productService.getMeasuringRates());
        model.addAttribute("providers",productService.getProviders());
        return "product/addProduct";
    }
    @GetMapping("addCategory")
    public String addCategory(Model model,@RequestParam String category){
        productService.addCategory(category);
        return "redirect:/addProduct";
    }
    @GetMapping("addManufacturer")
    public String addManufacturer(@RequestParam String manufacturer){
        productService.addManufacturer(manufacturer);
        return "redirect:/addProduct";
    }
    @GetMapping("addMeasuring")
    public String addMeasuring(@RequestParam String measuring){
        productService.addMeasuring(measuring);
        return "redirect:/addProduct";
    }

    @PostMapping("addProduct")
    public String saveProduct(@RequestParam String barcode,
                              @RequestParam String productName,
                              @RequestParam Double providerPrice,
                              @RequestParam Double price,
                              @RequestParam String date,
                              @RequestParam Double vat,
                              @RequestParam(required = false, defaultValue = "") String description,
                              @RequestParam MultipartFile image,
                              @RequestParam Long category,
                              @RequestParam Long manufacturer,
                              @RequestParam Long measuring,
                              @RequestParam Long provider
                              ) throws IOException {
        ProviderProduct product = new ProviderProduct();

        product.setDescription(description);
        product.setName(productName);
        product.setVat(vat);
        saveFile(product,image);
        productService.saveProduct(product,manufacturer,measuring);

        productService.productAddProviderPrice(providerPrice,product);
        productService.productAddProductPrice(price,date,product);
        productService.productAddBarcode(barcode,product.getIdProviderProduct());
        productService.productAddCategory(category,product);
        productService.productAddProvider(provider,product);

         return "redirect:/myShop";
    }

    @GetMapping("allProducts")
    public String allProducts(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "product/allProducts";
    }

    @PostMapping(value = "/removeProducts")
    public @ResponseBody
    String removeProducts(
            @RequestBody String json
    ) throws IOException {
        List<String> list = new ObjectMapper().readValue(json, List.class);
    productService.removeProduct(list);
        System.out.println(list.get(0));
        return json;
    }

    private void saveFile(@Valid ProviderProduct product, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            product.setPhoto(resultFileName);
        }
    }
}
