package com.diploma.cashregister.controller;

import com.diploma.cashregister.domain.ProviderProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MyShopController {


    @GetMapping("addProduct")
    public String addProduct(){
        return "product/addProduct";
    }

    private void saveFile(@Valid ProviderProduct message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File("uploadPath");
            if (!uploadDir.exists()) uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File("uploadPath" + "/" + resultFileName));
            //message.setFilename(resultFileName);
        }
    }
}
