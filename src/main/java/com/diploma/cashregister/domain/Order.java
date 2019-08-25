package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(of = "idOrder")
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy=GenerationType.AUTO)
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
    private Set<Delivery> deliveries;

    @ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "id_provider")
    private Provider provider;

    @OneToMany(mappedBy = "order")
    private Collection<OrderPayments> orderPayments;

    @OneToMany(mappedBy = "order")
    private Collection<OrderBucket> orderBuckets;

    public void setDeliveries(Delivery delivery) {
        this.deliveries.add(delivery);
    }
}
