package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "provider_product")
@Data
@EqualsAndHashCode(of = "idProviderProduct")
@NoArgsConstructor
@ToString(of = {"idProviderProduct","name","vat","photo","description"})
public class ProviderProduct {
    @Id
    @Column(name = "id_provider_product")
    @SequenceGenerator(name="provider_product_id_provider_product_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="provider_product_id_provider_product_seq")
    private long idProviderProduct;

    private String name;
    private Double vat;
    private String photo;
    private String description;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Action> actions = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Bucket> buckets = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<DeliveryBasket> deliveryBaskets = new HashSet<>();

    @OneToMany(mappedBy = "providerProductMeasuringRate")
    private Collection<MeasuringRateConnectProviderProduct> measuringRateConnectProviderProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Price> prices = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ProductConnectCategory> productConnectCategories = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ProviderConnectProduct> providerConnectProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ProviderPrice> providerPrices = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "base_measuring_rate", referencedColumnName = "id_provider_product_measuring_rate")
    private ProviderProductMeasuringRate providerProductMeasuringRate;

    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "id_manufacturer")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "providerProduct")
    private Collection<ReturnedProduct> returnedProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<WrittenOffProduct> writtenOffProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<Barcode> barcodes = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct")
    private Collection<OrderBucket> orderBuckets = new HashSet<>();

    public Price getCurrentPrice(){
        return prices.stream().filter(el->LocalDate.now().isAfter(el.getDateStart()) && LocalDate.now().isBefore(el.getDateFinish()))
                .findAny()
                .orElse(null);
    }

}
