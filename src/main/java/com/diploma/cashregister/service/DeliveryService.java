package com.diploma.cashregister.service;

import com.diploma.cashregister.domain.*;
import com.diploma.cashregister.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    @Autowired
    private final ProviderRepo providerRepo;
    @Autowired
    private final ProductRepo productRepo;

    public DeliveryService(OrderRepo orderRepo, OrderBucketRepo orderBucketRepo, OrderPaymentsRepo orderPaymentsRepo, DeliveryRepo deliveryRepo, DeliveryBasketRepo deliveryBasketRepo, ProviderRepo providerRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.orderBucketRepo = orderBucketRepo;
        this.orderPaymentsRepo = orderPaymentsRepo;
        this.deliveryRepo = deliveryRepo;
        this.deliveryBasketRepo = deliveryBasketRepo;
        this.providerRepo = providerRepo;
        this.productRepo = productRepo;
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

    @Transactional
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
        orderFromDb.setStatus("accepted");

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

    public List<Provider> getProviders() {
        return providerRepo.findAll();
    }

    @Transactional
    public void updateOrder (Map<String, String> map) {
        Float price = Float.valueOf(map.get("price"));
        long id = Integer.parseInt(map.get("order"));

        Order order = orderRepo.findById(id).get();
        order.setDateOfWishingDelivery(LocalDate.parse(map.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        order.setStatus("updated on " + LocalDate.now());
        order.setPrice(price);
        order.setProvider(providerRepo.findById(Long.valueOf(map.get("provider"))).get());
        orderRepo.save(order);

        double onDelivery = Double.parseDouble(map.get("onDelivery"));
        double prepayment = Double.parseDouble(map.get("prepayment"));

        orderPaymentsRepo.findWhereOrders(id).forEach(payment ->{
            if (payment.getType().equals("pre")){
                payment.setPercent(prepayment/price/0.01);
                payment.setSum(prepayment);
            }else {
                payment.setPercent(onDelivery/price/0.01);
                payment.setSum(onDelivery);
            }
            orderPaymentsRepo.save(payment);
        });
        orderBucketRepo.findAllByOrder(id).forEach(e-> orderBucketRepo.delete(e));
        fillOrderBucket(map, order);
    }

    private void fillOrderBucket(Map<String, String> map, Order order) {
        map.forEach((k, v) -> {
            try {
                long id = Long.parseLong(k);
                int count = Integer.parseInt(v);
                ProviderProduct product = productRepo.findById(id).get();
                OrderBucket bucket = new OrderBucket();
                bucket.setAmount(count);
                bucket.setCurrency("PLN");
                bucket.setMeasuringRate(product.getProviderProductMeasuringRate().getName());
                bucket.setOrder(order);
                bucket.setPricePerUnit(product.getCurrentProviderPrice());
                bucket.setProviderProduct(product);
                orderBucketRepo.save(bucket);
            } catch (NumberFormatException e) {
            }
        });
    }

    @Transactional
    public void createOrder(Map<String, String> map) {
        Float price = Float.valueOf(map.get("price"));

        Order order = new Order();
        order.setDateOfCreate(LocalDate.now());
        order.setDateOfWishingDelivery(LocalDate.parse(map.get("date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        order.setStatus("created");
        order.setPrice(price);
        order.setProvider(providerRepo.findById(Long.valueOf(map.get("provider"))).get());
        orderRepo.save(order);

        double onDelivery = Double.parseDouble(map.get("onDelivery"));
        double prepayment = Double.parseDouble(map.get("prepayment"));

        OrderPayments prepayments = new OrderPayments();
        prepayments.setCurrency("PLN");
        prepayments.setOrder(order);
        prepayments.setPercent(prepayment/price/0.01);
        prepayments.setSum(prepayment);
        prepayments.setType("pre");

        orderPaymentsRepo.save(prepayments);

        OrderPayments onPayments = new OrderPayments();
        onPayments.setCurrency("PLN");
        onPayments.setOrder(order);
        onPayments.setPercent(onDelivery/price/0.01);
        onPayments.setSum(onDelivery);
        onPayments.setType("onDelivery");
        orderPaymentsRepo.save(onPayments);

        fillOrderBucket(map, order);
    }

    public List<Order> getOrders() {
        return orderRepo.findAll();
    }
}
