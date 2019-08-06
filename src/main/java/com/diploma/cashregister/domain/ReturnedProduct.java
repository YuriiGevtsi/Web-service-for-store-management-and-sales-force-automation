package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "returned_product")
@Data
@EqualsAndHashCode(of = "idReturnedProduct")
@NoArgsConstructor
public class ReturnedProduct {
    @Id
    @Column(name = "id_returned_product")
    private long idReturnedProduct;
    private LocalDate date;

    private String reason;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
