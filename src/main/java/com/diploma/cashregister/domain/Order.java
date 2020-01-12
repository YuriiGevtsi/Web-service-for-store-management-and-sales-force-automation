package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(of = "idOrder")
@NoArgsConstructor
@ToString(of = {"idOrder","dateOfWishingDelivery","status","dateOfCreate","price"})
public class Order {
    @Id
    @Column(name = "id_order")
    @SequenceGenerator(name="orders_id_order_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="orders_id_order_seq")
    private long idOrder;

    @Column(name = "date_of_wishing_delivery")
    private LocalDate dateOfWishingDelivery;

    @Column(name = "status")
    private String status;

    @Column(name = "place")
    private String place;

    @Column(name = "date_of_create")
    private LocalDate dateOfCreate;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Float price;

    @Column(name = "amount_of_delivery")
    private String amountOfDelivery;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "orders")
    private Set<Delivery> deliveries = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "id_provider")
    private Provider provider;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private Collection<OrderPayments> orderPayments = new HashSet<>();

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private Collection<OrderBucket> orderBuckets = new HashSet<>();

    public void setDeliveries(Delivery delivery) {
        this.deliveries.add(delivery);
    }
}
