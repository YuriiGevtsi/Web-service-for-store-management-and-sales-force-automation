package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.Barcode;
import com.diploma.cashregister.domain.ProviderProduct;
import com.diploma.cashregister.domain.WrittenOffProduct;
import com.diploma.cashregister.repos.BarcodeRepo;
import com.diploma.cashregister.repos.ProductRepo;
import com.diploma.cashregister.repos.WrittenOffProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private final ProductRepo productRepo;
    @Autowired
    private final BarcodeRepo barcodeRepo;
    @Autowired
    private final WrittenOffProductRepo writtenOffProductRepo;

    public ProductService(ProductRepo productRepo, BarcodeRepo barcodeRepo, WrittenOffProductRepo writtenOffProductRepo) {
        this.productRepo = productRepo;
        this.barcodeRepo = barcodeRepo;
        this.writtenOffProductRepo = writtenOffProductRepo;
    }

    public  ProviderProduct findProductByBarcode(String code){
        Barcode barcode = barcodeRepo.findBarcode(code);
        ProviderProduct product = barcode == null ? null : barcode.getProviderProduct();
        return product;
    }

    public void saveWrittenOffProduct(WrittenOffProduct product){
        writtenOffProductRepo.save(product);
    }
}
