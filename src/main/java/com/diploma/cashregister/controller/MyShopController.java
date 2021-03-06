package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.ProviderProduct;
import com.diploma.cashregister.service.EmployeeService;
import com.diploma.cashregister.service.ProductService;
import com.diploma.cashregister.service.SellingOperationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
import java.util.*;

@Controller
public class MyShopController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final SellingOperationService sellingOperationService;

    @Value("${upload.path}")
    private String uploadPath;

    public MyShopController(ProductService productService, EmployeeService employeeService, SellingOperationService sellingOperationService) {
        this.productService = productService;
        this.sellingOperationService = sellingOperationService;
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
                              @RequestParam List<Long> provider,
                              @RequestParam(required = false, defaultValue = "-1") Long idProduct
    ) throws IOException {
        ProviderProduct product;

        if (idProduct!=-1){
            product = productService.getProduct(String.valueOf(idProduct));
            setBasicParameters(productName, vat, description, image, product);

            productService.editProduct(product,manufacturer,measuring,providerPrice,price,date,barcode,category,provider);
        }
        else{
            product = new ProviderProduct();
            setBasicParameters(productName, vat, description, image, product);
            try {
                productService.createProduct(product,manufacturer,measuring,providerPrice,price,date,barcode,category,provider);
            }catch (Exception e){

            }
        }

        if (idProduct == -1)return "redirect:/myShop";
        else return "redirect:/allProducts";
    }

    private void setBasicParameters(@RequestParam String productName, @RequestParam Double vat, @RequestParam(required = false, defaultValue = "") String description, @RequestParam MultipartFile image, ProviderProduct product) throws IOException {
        product.setDescription(description);
        product.setName(productName);
        product.setVat(vat);
        saveFile(product, image);
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
        return json;
    }

    @GetMapping("editProduct")
    public String editProduct(Model model, @RequestParam String idProduct){
        model.addAttribute("categories",productService.getCategories());
        model.addAttribute("manufacturers",productService.getManufacturers());
        model.addAttribute("measuringRates",productService.getMeasuringRates());
        model.addAttribute("providers",productService.getProviders());
        model.addAttribute("product", productService.getProduct(idProduct));
        return "product/addProduct";
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

    @GetMapping("sales")
    public String sales(Model model, @RequestParam(required = false, defaultValue = "") String from,@RequestParam(required = false, defaultValue = "") String to){
        if (!from.isEmpty() && !to.isEmpty()){
            model.addAttribute("products", sellingOperationService.findSellingsByPeriod(from,to));
            model.addAttribute("from", LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            model.addAttribute("to", LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }else {
            model.addAttribute("products", sellingOperationService.findSellings());
        }
        return "sales/sales";
    }
    @GetMapping("store")
    public String store(Model model){
        model.addAttribute("products",productService.showStore());
        return "product/store";
    }
    @GetMapping("shifts")
    public String shifts(Model model){
        return "employee/shifts";
    }
    @GetMapping("inventory")
    public String inventory(Model model){
        model.addAttribute("products",productService.showStore());
        return "product/inventory";
    }

    @PostMapping(value = "/saveInventory")
    public @ResponseBody
    String saveInventory(
            @RequestBody String json
    ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<String>> list = objectMapper.readValue(json, new TypeReference<List<List<String>>>(){});
        productService.saveInventory(list);
        return json;
    }

    @GetMapping("createSale")
    public String createSale(Model model){
        model.addAttribute("products",productService.getAllProducts());
        return "sales/createSale";
    }
    @GetMapping("discounts")
    public String discounts(Model model){
        //model.addAttribute("products",productService.getAllProducts());
        return "product/discounts";
    }

    @PostMapping("createSale")
    @ResponseBody
    public  String createSale(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(json, new TypeReference<Map<String,String>>(){});
        sellingOperationService.createSale(map);
        return "200";
    }


}
