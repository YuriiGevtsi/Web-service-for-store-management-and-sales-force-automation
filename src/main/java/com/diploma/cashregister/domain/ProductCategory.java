package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "product_category")
@Data@ToString(of = "name")
@EqualsAndHashCode(of = "idProductCategory")
@NoArgsConstructor
public class ProductCategory {
    @Id
    @Column(name = "id_product_category")
    @SequenceGenerator(name="product_category_id_product_category_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="product_category_id_product_category_seq")
    private long idProductCategory;

    private String name;

    @OneToMany(mappedBy = "productCategory", orphanRemoval = true)
    private Collection<ProductConnectCategory> productConnectCategories = new HashSet<>();

}
