package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;

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

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<Action> actions = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<Bucket> buckets = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<DeliveryBasket> deliveryBaskets = new HashSet<>();

    @OneToMany(mappedBy = "providerProductMeasuringRate", orphanRemoval = true)
    private Collection<MeasuringRateConnectProviderProduct> measuringRateConnectProviderProducts = new HashSet<>();


    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<Price> prices = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<ProductConnectCategory> productConnectCategories = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<ProviderConnectProduct> providerConnectProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<ProviderPrice> providerPrices = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "base_measuring_rate", referencedColumnName = "id_provider_product_measuring_rate")
    private ProviderProductMeasuringRate providerProductMeasuringRate;

    @ManyToOne
    @JoinColumn(name = "manufacturer", referencedColumnName = "id_manufacturer")
    private Manufacturer manufacturer;

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<ReturnedProduct> returnedProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<WrittenOffProduct> writtenOffProducts = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<Barcode> barcodes = new HashSet<>();

    @OneToMany(mappedBy = "providerProduct", orphanRemoval = true)
    private Collection<OrderBucket> orderBuckets = new HashSet<>();

    public Price getCurrentPrice(){
        Optional<Price> price = prices.stream().filter(el ->
                (LocalDate.now().isAfter(el.getDateStart()) || LocalDate.now().isEqual(el.getDateStart()))
                        && (LocalDate.now().isBefore(el.getDateFinish()) || LocalDate.now().isEqual(el.getDateFinish())) )
                            .min(Comparator.comparingLong(x -> ChronoUnit.DAYS.between(x.getDateStart(), LocalDate.now())));
        return price.isPresent() ? price.get() : null;
    }
    public Double getCurrentProviderPrice(){
        Optional<ProviderPrice> price = providerPrices.stream()
                            .min(Comparator.comparingLong(x -> ChronoUnit.DAYS.between(x.getDate(), LocalDate.now())));
        return price.isPresent() ? price.get().getPrice() : 0;
    }

    public Barcode getCurrentBarcode(){
        return barcodes.stream().findAny().orElse(null);
    }

    public ProductCategory getMainCategory(){
        return getProductConnectCategories().stream().findAny().get().getProductCategory();
    }

    public boolean findProvider(Long idProviderProduct){
        return providerConnectProducts.stream().anyMatch(el->el.getProvider().getIdProvider() == idProviderProduct);
    }

    public boolean checkProvider(Long idProvider){
        return providerConnectProducts.stream().anyMatch(e -> e.getProvider().getIdProvider() == idProvider);
    }
}
