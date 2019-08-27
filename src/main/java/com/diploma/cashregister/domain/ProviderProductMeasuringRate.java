package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "provider_product_measuring_rate")
@Data
@EqualsAndHashCode(of = "idProviderProductMeasuringRate")
@NoArgsConstructor
public class ProviderProductMeasuringRate {
    @Id
    @Column(name = "id_provider_product_measuring_rate")
    @SequenceGenerator(name="provider_product_measuring_rate_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="provider_product_measuring_rate_seq")
    private long idProviderProductMeasuringRate;

    private String name;

    @OneToMany(mappedBy = "providerProductMeasuringRate")
    private Collection<MeasuringRateConnectProviderProduct> measuringRateConnectProviderProducts;

    @OneToMany(mappedBy = "providerProductMeasuringRate")
    private Collection<ProviderProduct> providerProducts;

}
