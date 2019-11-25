package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.Bucket;
import com.diploma.cashregister.domain.FinancialOperations;
import com.diploma.cashregister.domain.SellingOperation;
import com.diploma.cashregister.repos.BucketRepo;
import com.diploma.cashregister.repos.FinancialOperationRepo;
import com.diploma.cashregister.repos.PriceRepo;
import com.diploma.cashregister.repos.SellingOperationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class SellingOperationService {
    @Autowired
    private final BucketRepo bucketRepo;

    @Autowired
    private final FinancialOperationRepo financialOperationRepo;

    @Autowired
    private final SellingOperationRepo sellingOperationRepo;
    @Autowired
    private final PriceRepo priceRepo;

    public SellingOperationService(BucketRepo bucketRepo, FinancialOperationRepo financialOperationRepo, SellingOperationRepo sellingOperationRepo, PriceRepo priceRepo) {
        this.bucketRepo = bucketRepo;
        this.financialOperationRepo = financialOperationRepo;
        this.sellingOperationRepo = sellingOperationRepo;
        this.priceRepo = priceRepo;
    }

    public Set<Bucket> getReceiptProducts (Long number){
        return bucketRepo.findReceiptProducts(number);
    }

    public void saveFinancialOperation(FinancialOperations financialOperations) {financialOperationRepo.save(financialOperations);}

    public boolean findReceipt(long number) {
        return sellingOperationRepo.findById(number).orElse(null) == null;
    }

    public void returnProduct(Map<String, Map<String, String>> map) {
        Set<Bucket> products = getReceiptProducts(Long.valueOf(map.get("receipt").get("id")));
        final Double[] price = {0.0};

        products.forEach(prod->{
            String count,comment;
            count = map.get(String.valueOf(prod.getIdBucket())).get("count");
//            comment = map.get(prod.getProviderProduct().getIdProviderProduct()).get("comment");
            if (!count.isEmpty()){
                prod.setCount(prod.getCount() - Double.parseDouble(count));
                price[0] += prod.getProviderProduct().getCurrentPrice().getPrice() * Double.parseDouble(count);
                bucketRepo.save(prod);
            }
        });

        SellingOperation receipt = sellingOperationRepo.findById(Long.valueOf(map.get("receipt").get("id"))).get();
        receipt.setSumm(receipt.getSumm() - price[0]);
        sellingOperationRepo.save(receipt);

    }
}
