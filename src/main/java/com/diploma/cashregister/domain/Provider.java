package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "provider")
@Data
@EqualsAndHashCode(of = "idProvider")
@NoArgsConstructor
public class Provider {
    @Id
    @Column(name = "id_provider")
    private long idProvider;

    private String name;
    private String address;
    private String eMail;

    @OneToMany(mappedBy = "vendor")
    private Collection<Delivery> deliveries;

    @OneToMany(mappedBy = "provider")
    private Collection<Order> orders;

    @OneToMany(mappedBy = "provider")
    private Collection<ProviderConnectProduct> providerConnectProducts;

}
