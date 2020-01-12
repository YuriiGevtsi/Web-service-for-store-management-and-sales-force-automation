package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "manufacturer")
@Data
@EqualsAndHashCode(of = "idManufacturer")
@NoArgsConstructor
public class Manufacturer {
    @Id
    @Column(name = "id_manufacturer")
    @SequenceGenerator(name="manufacturer_id_manufacturer_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="manufacturer_id_manufacturer_seq")
    private long idManufacturer;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "manufacturer", orphanRemoval = true)
    private Collection<ProviderProduct> providerProducts = new HashSet<>();

}
