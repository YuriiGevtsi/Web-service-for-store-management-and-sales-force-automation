package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.*;
import com.diploma.cashregister.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private final ProductRepo productRepo;
    @Autowired
    private final BarcodeRepo barcodeRepo;
    @Autowired
    private final WrittenOffProductRepo writtenOffProductRepo;
    @Autowired
    private final CategoryRepo categoryRepo;
    @Autowired
    private final ManufacturerRepo manufacturerRepo;
    @Autowired
    private final MeasuringRepo measuringRepo;
    @Autowired
    private final ProviderRepo providerRepo;
    @Autowired
    private final ProviderPriceRepo providerPriceRepo;
    @Autowired
    private final PriceRepo priceRepo;
    @Autowired
    private final ProductConnectCategoryRepo productConnectCategoryRepo;
    @Autowired
    private final ProviderConnectProductRepo providerConnectProductRepo;
    @Autowired
    private final DeliveryBasketRepo deliveryBasketRepo;
    @Autowired
    private final BucketRepo bucketRepo;
    @Autowired
    private final ReturnedProductRepo returnedProductRepo;
    @Autowired
    private final InventoryRepo inventoryRepo;

    public ProductService(ProductRepo productRepo, BarcodeRepo barcodeRepo, WrittenOffProductRepo writtenOffProductRepo, CategoryRepo categoryRepo, ManufacturerRepo manufacturerRepo, MeasuringRepo measuringRepo, ProviderRepo providerRepo, ProviderPriceRepo providerPriceRepo, PriceRepo priceRepo, ProductConnectCategoryRepo productConnectCategoryRepo, ProviderConnectProductRepo providerConnectProductRepo, DeliveryBasketRepo deliveryBasketRepo, BucketRepo bucketRepo, ReturnedProductRepo returnedProductRepo, InventoryRepo inventoryRepo) {
        this.productRepo = productRepo;
        this.barcodeRepo = barcodeRepo;
        this.writtenOffProductRepo = writtenOffProductRepo;
        this.categoryRepo = categoryRepo;
        this.manufacturerRepo = manufacturerRepo;
        this.measuringRepo = measuringRepo;
        this.providerRepo = providerRepo;
        this.providerPriceRepo = providerPriceRepo;
        this.priceRepo = priceRepo;
        this.productConnectCategoryRepo = productConnectCategoryRepo;
        this.providerConnectProductRepo = providerConnectProductRepo;
        this.deliveryBasketRepo = deliveryBasketRepo;
        this.bucketRepo = bucketRepo;
        this.returnedProductRepo = returnedProductRepo;
        this.inventoryRepo = inventoryRepo;
    }

    public  ProviderProduct findProductByBarcode(String code){
        Barcode barcode = barcodeRepo.findBarcode(code);
        ProviderProduct product = barcode == null ? null : barcode.getProviderProduct();
        return product;
    }

    public void addCategory (String name){
        ProductCategory category = new ProductCategory();
        category.setName(name);
        categoryRepo.save(category);
    }

    public void addManufacturer(String name) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(name);
        manufacturerRepo.save(manufacturer);
    }

    public void saveWrittenOffProduct(WrittenOffProduct product){
        writtenOffProductRepo.save(product);
    }

    public void addMeasuring(String name) {
        ProviderProductMeasuringRate measuring = new ProviderProductMeasuringRate();
        measuring.setName(name);
        measuringRepo.save(measuring);
    }

    public List<ProductCategory> getCategories(){
        return categoryRepo.findAll();
    }
    public List<Manufacturer> getManufacturers(){
        return manufacturerRepo.findAll();
    }
    public List<ProviderProductMeasuringRate> getMeasuringRates(){
        return measuringRepo.findAll();
    }
    public List<Provider> getProviders(){
        return providerRepo.findAll();
    }

    public void productAddBarcode(String val, long idProviderProduct) {
        Barcode barcode = new Barcode();
        barcode.setCode(val);
        barcode.setProviderProduct(productRepo.findById(idProviderProduct).get());
        barcodeRepo.save(barcode);
    }

    public void saveProduct(ProviderProduct product, Long manufacturer, Long measuring) {
        product.setManufacturer(manufacturerRepo.findById(manufacturer).get());
        product.setProviderProductMeasuringRate(measuringRepo.findById(measuring).get());
        productRepo.save(product);
    }

    public void productAddProviderPrice(Double price, ProviderProduct product) {
        ProviderPrice providerPrice = new ProviderPrice();
        providerPrice.setCurrency("pln");
        providerPrice.setDate(LocalDate.now());
        providerPrice.setPrice(price);
        providerPrice.setProviderProduct(product);

        Collection<ProviderPrice> providerPrices = product.getProviderPrices();
        providerPrices.add(providerPrice);
        product.setProviderPrices(providerPrices);

        providerPriceRepo.save(providerPrice);
        productRepo.save(product);
    }

    public void productAddProductPrice(Double price, String date, ProviderProduct product) {
        Price prodPrice = new Price();
        prodPrice.setDateStart(LocalDate.now());
        prodPrice.setDateFinish(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        prodPrice.setPrice(price);
        prodPrice.setProviderProduct(product);

        Collection<Price> prices = product.getPrices();
        prices.add(prodPrice);
        product.setPrices(prices);

        priceRepo.save(prodPrice);
        productRepo.save(product);
    }

    public void productAddCategory(Long category, ProviderProduct product) {
        ProductConnectCategory productConnectCategory = new ProductConnectCategory();
        productConnectCategory.setProductCategory(categoryRepo.findById(category).get());
        productConnectCategory.setProviderProduct(product);

        Collection<ProductConnectCategory> productConnectCategories = product.getProductConnectCategories();
        productConnectCategories.add(productConnectCategory);
        product.setProductConnectCategories(productConnectCategories);

        productConnectCategoryRepo.save(productConnectCategory);
        productRepo.save(product);
    }

    public void productAddProvider(Long provider, ProviderProduct product) {
        ProviderConnectProduct providerConnectProduct = new ProviderConnectProduct();
        providerConnectProduct.setProvider(providerRepo.findById(provider).get());
        providerConnectProduct.setProviderProduct(product);

        Collection<ProviderConnectProduct> providerConnectProducts = product.getProviderConnectProducts();
        providerConnectProducts.add(providerConnectProduct);
        product.setProviderConnectProducts(providerConnectProducts);

        providerConnectProductRepo.save(providerConnectProduct);
        productRepo.save(product);
    }

    public List<ProviderProduct>  getAllProducts() {
        return productRepo.findAll();
    }
    public List<ProviderProduct> getAllProductsByProvider(long provider) {
        return productRepo.findAll().stream().filter(product -> product.findProvider(provider)).collect(Collectors.toList());
    }
    public ProviderProduct  getProduct(String id) {
        return productRepo.findById(Long.valueOf(id)).get();
    }

    public void removeProduct(List<String> list) {
        list.forEach(e->{
            ProviderProduct product = productRepo.findById(Long.valueOf(e)).get();
            productRepo.delete(product);
        });
    }

    public void productEditPrice(Double price, String date, ProviderProduct product) {
        Price currentPrice = product.getCurrentPrice();
        currentPrice.setDateFinish(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        currentPrice.setPrice(price);
        priceRepo.save(currentPrice);
    }

    public void editCategory(Long category, ProviderProduct product) {
        ProductCategory mainCategory = product.getMainCategory();
        mainCategory.setName(categoryRepo.findById(category).get().getName());
        categoryRepo.save(mainCategory);
    }

    @Transactional
    public void editProduct(ProviderProduct product, Long manufacturer, Long measuring, Double providerPrice, Double price, String date, String barcode, Long category, List<Long> provider) {
        saveProduct(product,manufacturer,measuring);

        if (product.getCurrentProviderPrice() == null || !product.getCurrentProviderPrice().equals(providerPrice)) productAddProviderPrice(providerPrice,product);
        productEditPrice(price,date,product);
        if (product.getCurrentBarcode() == null || !product.getCurrentBarcode().getCode().equals(barcode)) productAddBarcode(barcode,product.getIdProviderProduct());
        if (product.getMainCategory() == null || product.getMainCategory().getIdProductCategory() != category) editCategory(category,product);
        provider.forEach(e-> {
            if (!product.findProvider(e))productAddProvider(e,product);
        });
    }

    @Transactional
    public void createProduct(ProviderProduct product, Long manufacturer, Long measuring, Double price, Double providerPrice, String date, String barcode, Long category, List<Long> provider) {
        saveProduct(product,manufacturer,measuring);
        productAddProviderPrice(providerPrice,product);
        productAddProductPrice(price,date,product);
        productAddBarcode(barcode,product.getIdProviderProduct());
        productAddCategory(category,product);
        provider.forEach(e-> {
            productAddProvider(e,product);
        });
    }

    public HashMap<ProviderProduct, Double> showStore() {
        HashMap<ProviderProduct,Double> table = new HashMap<>();
        productRepo.findAll().stream().forEach(el-> table.put(el,0.0));

        deliveryBasketRepo.findAll().stream().forEach(el->{
            double count = table.get(el.getProviderProduct()) + el.getAmount();
            table.replace(el.getProviderProduct(), count);
        });

        returnedProductRepo.findAll().stream().forEach(el->{
            double count = table.get(el.getProviderProduct()) + el.getAmount();
            table.replace(el.getProviderProduct(), count);
        });

        bucketRepo.findAll().stream().forEach(el->{
            double count = table.get(el.getProviderProduct()) - el.getCount();
            table.replace(el.getProviderProduct(), count);
        });

        writtenOffProductRepo.findAll().stream().forEach(el->{
            double count = table.get(el.getProviderProduct()) - el.getAmount();
            table.replace(el.getProviderProduct(), count);
        });

        return table;
    }

    public void saveInventory(List<List<String>> list) {
        list.stream().forEach(e->{
            Inventory inventory = new Inventory();
            inventory.setDate(LocalDate.parse(e.get(2)));
            inventory.setDifference(Integer.parseInt(e.get(1)));
            inventory.setProduct(productRepo.findById(Long.valueOf(e.get(0))).get());
            inventoryRepo.save(inventory);
        });
    }
}
