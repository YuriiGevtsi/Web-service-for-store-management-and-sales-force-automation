package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "barcode")
@Data
@EqualsAndHashCode(of = "idBarcode")
@NoArgsConstructor
public class Barcode {
    @Id
    @Column(name = "id_barcode")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long idBarcode;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
