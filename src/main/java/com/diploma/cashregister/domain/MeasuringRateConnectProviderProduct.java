package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "measuring_rate_connect_provider_product")
@Data
@EqualsAndHashCode(of = "idMeasuringRateConnectProviderProduct")
@NoArgsConstructor
public class MeasuringRateConnectProviderProduct {
    @Id
    @Column(name = "id_measuring_rate_connect_provider_product")
    @SequenceGenerator(name="measuring_rate_connect_provider_product_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="measuring_rate_connect_provider_product_seq")
    private long idMeasuringRateConnectProviderProduct;

    @Column(name = "coefficient")
    private BigInteger coefficient;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

    @ManyToOne
    @JoinColumn(name = "measuring_rate", referencedColumnName = "id_provider_product_measuring_rate")
    private ProviderProductMeasuringRate providerProductMeasuringRate;

}
