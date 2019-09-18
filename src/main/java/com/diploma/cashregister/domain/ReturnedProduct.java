package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "returned_product")
@Data
@EqualsAndHashCode(of = "idReturnedProduct")
@NoArgsConstructor
public class ReturnedProduct {
    @Id
    @Column(name = "id_returned_product")
    @SequenceGenerator(name="returned_product_id_returned_product_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="returned_product_id_returned_product_seq")
    private long idReturnedProduct;
    private LocalDateTime date;

    private String reason;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
