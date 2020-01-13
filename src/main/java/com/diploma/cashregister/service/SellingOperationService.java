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

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    public Set<List> findSellings() {
        LocalDate from;
        LocalDate to;
        Set<List> table = new HashSet<>();
        Set<Bucket> list1 = new HashSet<>();
        HashMap<Long,Double> map = new HashMap<>();

        bucketRepo.findAll().stream().forEach(bucket -> {
            if (map.keySet().contains(bucket.getProviderProduct().getIdProviderProduct())){
                double d = map.get(bucket.getProviderProduct().getIdProviderProduct());
                d+=bucket.getSellingOperation().getSumm();
                map.put(bucket.getProviderProduct().getIdProviderProduct(),d);
            }else {
                list1.add(bucket);
                map.put(bucket.getProviderProduct().getIdProviderProduct(),bucket.getSellingOperation().getSumm());
            }
        });

        collectList(table, list1, map);
        return table;
    }

    private void collectList(Set<List> table, Set<Bucket> list1, HashMap<Long, Double> map) {
        list1.forEach(el->{
            List<String> row = new ArrayList<>();
            row.add(el.getProviderProduct().getCurrentBarcode().getCode());
            row.add(el.getProviderProduct().getName());
            row.add(el.getProviderProduct().getProviderProductMeasuringRate().getName());
            row.add(el.getProviderProduct().getVat().toString());
            row.add(String.valueOf(el.getProviderProduct().getCurrentPrice().getPrice()));
            row.add(String.valueOf(map.get(el.getProviderProduct().getIdProviderProduct())));
            table.add(row);
        });
    }

    public Set<List> findSellingsByPeriod(String from, String to) {
        LocalDate dfrom = LocalDate.parse(from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate dto = LocalDate.parse(to, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Set<List> table = new HashSet<>();
        Set<Bucket> list1 = new HashSet<>();
        HashMap<Long,Double> map = new HashMap<>();
        bucketRepo.findAll().stream().filter(el ->
                (ChronoLocalDate.from(el.getSellingOperation().getDate()).isAfter(dfrom) || ChronoLocalDate.from(el.getSellingOperation().getDate()).isEqual(dfrom))
                        && (ChronoLocalDate.from(el.getSellingOperation().getDate()).isBefore(dto) || ChronoLocalDate.from(el.getSellingOperation().getDate()).isEqual(dto))).forEach(bucket -> {
            if (map.keySet().contains(bucket.getProviderProduct().getIdProviderProduct())){
                double d = map.get(bucket.getProviderProduct().getIdProviderProduct());
                d+=bucket.getSellingOperation().getSumm();
                map.put(bucket.getProviderProduct().getIdProviderProduct(),d);
            }else {
                list1.add(bucket);
                map.put(bucket.getProviderProduct().getIdProviderProduct(),bucket.getSellingOperation().getSumm());
            }
        });

        collectList(table, list1, map);
        return table;
    }
}
