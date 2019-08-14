package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
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

    @OneToMany(mappedBy = "delivery")
    private Collection<DeliveryBasket> delivery_baskets;

    @ManyToMany
    @JoinTable(name="delivery_connect_order",
            joinColumns = @JoinColumn(name="id_delivery", referencedColumnName="id_delivery"),
            inverseJoinColumns = @JoinColumn(name="id_order", referencedColumnName="id_order")
    )
    private Set<Order> orders;

}
