package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_payments")
@Data
@EqualsAndHashCode(of = "idOrderPayments")
@NoArgsConstructor
public class OrderPayments {
    @Id
    @Column(name = "id_order_payments")
    @SequenceGenerator(name="order_payments_id_order_payments_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_payments_id_order_payments_seq")
    private long idOrderPayments;

    @Column(name = "type")
    private String type;
    private double sum;
    private double percent;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "orders", referencedColumnName = "id_order")
    private Order order;

}
