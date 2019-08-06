package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "client_card")
@Data
@EqualsAndHashCode(of = "idCard")
@NoArgsConstructor
public class ClientCard {
    @Id
    @Column(name = "id_card")
    private String idCard;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id_client")
    private Client client;

    @OneToMany(mappedBy = "clientCard")
    private Collection<SellingOperation> sellingOperations;

}
