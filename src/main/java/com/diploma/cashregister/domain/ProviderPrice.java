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
    @SequenceGenerator(name="provider_price_id_provider_price_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="provider_price_id_provider_price_seq")
    private long idProviderPrice;

    private LocalDate date;
    private double price;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
