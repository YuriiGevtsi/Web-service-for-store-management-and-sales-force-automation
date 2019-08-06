package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "client")
@Data
@EqualsAndHashCode(of = "idClient")
@NoArgsConstructor
public class Client {
    @Id
    @Column(name = "id_client")
    private long idClient;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "client")
    private Set<ClientCard> clientCards;


}
