package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "delivery_basket")
@Data
@EqualsAndHashCode(of = "idDeliveryBasket")
@NoArgsConstructor
public class DeliveryBasket {
    //nahera nada id?
    @Id
    @Column(name = "id_delivery_basket")
    private long idDeliveryBasket;

    @Column(name = "price")
    private long price;

    @Column(name = "measuring_rate")
    private String measuringRate;

    @Column(name = "amount")
    private long amount;

    @ManyToOne
    @JoinColumn(name = "delivery", referencedColumnName = "id_delivery")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
