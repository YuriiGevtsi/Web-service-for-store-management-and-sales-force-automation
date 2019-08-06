package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bucket")
@Data
@EqualsAndHashCode(of = "idBucket")
@NoArgsConstructor
public class Bucket {
    @Id
    @Column(name = "id_bucket")
    private long idBucket;

    @Column(name = "count")
    private double count;

    @ManyToOne
    @JoinColumn(name = "selling_operation", referencedColumnName = "id_selling")
    private SellingOperation sellingOperation;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
