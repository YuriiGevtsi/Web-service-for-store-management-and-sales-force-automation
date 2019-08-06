package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "provider_product")
@Data
@EqualsAndHashCode(of = "idProviderProduct")
@NoArgsConstructor
public class ProviderProduct {
    @Id
    @Column(name = "id_provider_product")
    private long idProviderProduct;

    private String name;
    private Double vat;
    private String photo;
    private String description;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Action> actions;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Bucket> buckets;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<DeliveryBasket> deliveryBaskets;

    @OneToMany(mappedBy = "providerProductMeasuringRate")
    private Collection<MeasuringRateConnectProviderProduct> measuringRateConnectProviderProducts;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Price> prices;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ProductConnectCategory> productConnectCategories;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ProviderConnectProduct> providerConnectProducts;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ProviderPrice> providerPrices;

    @ManyToOne
    @JoinColumn(name = "base_measuring_rate", referencedColumnName = "id_provider_product_measuring_rate")
    private ProviderProductMeasuringRate providerProductMeasuringRate;

    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "id_manufacturer")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ReturnedProduct> returnedProducts;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<WrittenOffProduct> writtenOffProducts;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Barcode> barcodes;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<OrderBucket> orderBuckets;

}
