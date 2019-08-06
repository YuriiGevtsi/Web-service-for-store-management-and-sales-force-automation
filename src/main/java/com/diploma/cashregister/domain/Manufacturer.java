package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "manufacturer")
@Data
@EqualsAndHashCode(of = "idManufacturer")
@NoArgsConstructor
public class Manufacturer {
    @Id
    @Column(name = "id_manufacturer")
    private long idManufacturer;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "manufacturer")
    private Collection<ProviderProduct> providerProducts;

}
