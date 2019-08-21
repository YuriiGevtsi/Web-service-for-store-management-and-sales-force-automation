package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.*;
import com.diploma.cashregister.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class DeliveryService {
    @Autowired
    private final OrderRepo orderRepo;

    @Autowired
    private final OrderBucketRepo orderBucketRepo;

    @Autowired
    private final OrderPaymentsRepo orderPaymentsRepo;

    @Autowired
    private final DeliveryRepo deliveryRepo;

    @Autowired
    private final DeliveryBasketRepo deliveryBasketRepo;

    public DeliveryService(OrderRepo orderRepo, OrderBucketRepo orderBucketRepo, OrderPaymentsRepo orderPaymentsRepo, DeliveryRepo deliveryRepo, DeliveryBasketRepo deliveryBasketRepo) {
        this.orderRepo = orderRepo;
        this.orderBucketRepo = orderBucketRepo;
        this.orderPaymentsRepo = orderPaymentsRepo;
        this.deliveryRepo = deliveryRepo;
        this.deliveryBasketRepo = deliveryBasketRepo;
    }

    public Order getOrder(long id){
        return orderRepo.findById(id).orElse(null);
    }

    public Set<OrderBucket> getProductFromOrderBucket(long id){
        return orderBucketRepo.findAllByOrder(id);
    }

    public Set<OrderPayments> getOrderPayments(long number) {
        return orderPaymentsRepo.findWhereOrders(number);
    }

    public void createDelivery(Map<String, String> map) {
        long id = Long.valueOf(map.get("order"));

        Order orderFromDb = orderRepo.getOne(id);
        Set<OrderBucket> bucketFromDb = orderBucketRepo.findAllByOrder(id);
        Delivery delivery = new Delivery();


        delivery.setDate(LocalDateTime.now());
        delivery.setOrders(orderFromDb);
        delivery.setStatus("ok");
        delivery.setVendor(orderFromDb.getProvider());

        orderFromDb.setDeliveries(delivery);

        deliveryRepo.save(delivery);
        orderRepo.save(orderFromDb);

        bucketFromDb.forEach(item->{
            DeliveryBasket deliveryItem = new DeliveryBasket();

            String amount =map.get(String.valueOf(item.getProviderProduct().getIdProviderProduct())).replace("\"","");
            deliveryItem.setAmount(Double.valueOf(amount));
            deliveryItem.setDelivery(delivery);
            deliveryItem.setMeasuringRate(item.getMeasuringRate());
            deliveryItem.setProviderProduct(item.getProviderProduct());
            deliveryItem.setPrice((deliveryItem.getAmount() * item.getPricePerUnit()));

            delivery.setDelivery_baskets(deliveryItem);
            deliveryBasketRepo.save(deliveryItem);
        });
    }
}
