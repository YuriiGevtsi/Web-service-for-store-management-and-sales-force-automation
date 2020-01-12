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
@Table(name = "provider")
@Data
@EqualsAndHashCode(of = "idProvider")
@NoArgsConstructor
@ToString(of = {"idProvider","name","address","eMail"})
public class Provider {
    @Id
    @Column(name = "id_provider")
    @SequenceGenerator(name="provider_id_provider_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="provider_id_provider_seq")
    private long idProvider;

    private String name;
    private String address;
    private String eMail;

    @OneToMany(mappedBy = "vendor", orphanRemoval = true)
    private Collection<Delivery> deliveries = new HashSet<>();

    @OneToMany(mappedBy = "provider", orphanRemoval = true)
    private Collection<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "provider", orphanRemoval = true)
    private Collection<ProviderConnectProduct> providerConnectProducts = new HashSet<>();

}
