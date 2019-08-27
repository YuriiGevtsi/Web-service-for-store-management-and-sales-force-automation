package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "provider_connect_product")
@Data
@EqualsAndHashCode(of = "idProviderConnectProduct")
@NoArgsConstructor
public class ProviderConnectProduct {
    @Id
    @Column(name = "id_provider_connect_product")
    @SequenceGenerator(name="provider_connect_product_id_provider_connect_product_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="provider_connect_product_id_provider_connect_product_seq")
    private long idProviderConnectProduct;

    @ManyToOne
    @JoinColumn(name = "provider", referencedColumnName = "id_provider")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
