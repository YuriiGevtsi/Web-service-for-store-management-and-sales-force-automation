package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "delivery")
@Data
@EqualsAndHashCode(of = "idDelivery")
@NoArgsConstructor
public class Delivery {
    @Id
    @Column(name = "id_delivery")
    @SequenceGenerator(name="delivery_id_delivery_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="delivery_id_delivery_seq")
    private long idDelivery;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "vendor", referencedColumnName = "id_provider")
    private Provider vendor;

    @OneToMany(mappedBy = "delivery", orphanRemoval = true)
    private Collection<DeliveryBasket> delivery_baskets = new HashSet<>();

    @ManyToMany
    @JoinTable(name="delivery_connect_order",
            joinColumns = @JoinColumn(name="id_delivery", referencedColumnName="id_delivery"),
            inverseJoinColumns = @JoinColumn(name="id_order", referencedColumnName="id_order")
    )
    private Set<Order> orders = new HashSet<>();

    public void setOrders(Order order) {
        this.orders.add(order);
    }

    public void setDelivery_baskets(DeliveryBasket delivery_baskets) {
        this.delivery_baskets.add(delivery_baskets);
    }
}
