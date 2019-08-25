package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "provider_price")
@Data
@EqualsAndHashCode(of = "idProviderPrice")
@NoArgsConstructor
public class ProviderPrice {
    @Id
    @Column(name = "id_provider_price")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long idProviderPrice;

    private LocalDate date;
    private double price;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
