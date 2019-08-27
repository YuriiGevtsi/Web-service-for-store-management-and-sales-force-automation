package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "order_bucket")
@Data
@ToString(of = {"idOrderBucket","amount","pricePerUnit","measuringRate","currency"})
@EqualsAndHashCode(of = "idOrderBucket")
@NoArgsConstructor
public class OrderBucket {
    @Id
    @Column(name = "id_order_bucket")
    @SequenceGenerator(name="order_bucket_id_order_bucket_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_bucket_id_order_bucket_seq")
    private long idOrderBucket;

    @Column(name = "amount")
    private double amount;

    @Column(name = "price_per_unit")
    private double pricePerUnit;

    @Column(name = "measuring_rate")
    private String measuringRate;

    @Column(name = "currency")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "orders", referencedColumnName = "id_order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
