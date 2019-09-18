package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "delivery_basket")
@Data
@EqualsAndHashCode(of = "idDeliveryBasket")
@ToString(of = {"idDeliveryBasket","amount","price","measuringRate"})
@NoArgsConstructor
public class DeliveryBasket {

    @Id
    @Column(name = "id_delivery_basket")
    @SequenceGenerator(name="delivery_basket_id_delivery_basket_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="delivery_basket_id_delivery_basket_seq")
    private long idDeliveryBasket;

    @Column(name = "price")
    private double price;

    @Column(name = "measuring_rate")
    private String measuringRate;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "delivery", referencedColumnName = "id_delivery")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
