package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
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
    @SequenceGenerator(name="client_id_client_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="client_id_client_seq")
    private long idClient;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "client", orphanRemoval = true)
    private Set<ClientCard> clientCards = new HashSet<>();


}
