package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_connect_category")
@Data
@EqualsAndHashCode(of = "idProductCategory")
@NoArgsConstructor
public class ProductConnectCategory {
    @Id
    @Column(name = "id_product_category")
    @SequenceGenerator(name="product_connect_category_id_product_category_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product_connect_category_id_product_category_seq")
    private long idProductCategory;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;


    @ManyToOne
    @JoinColumn(name = "product_category", referencedColumnName = "id_product_category")
    private ProductCategory productCategory;

}
