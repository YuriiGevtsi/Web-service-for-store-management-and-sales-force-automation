package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "product_category")
@Data
@EqualsAndHashCode(of = "idProductCategory")
@NoArgsConstructor
public class ProductCategory {
    @Id
    @Column(name = "id_product_category")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idProductCategory;

    private String name;

    @OneToMany(mappedBy = "productCategory")
    private Collection<ProductConnectCategory> productConnectCategories;

}
