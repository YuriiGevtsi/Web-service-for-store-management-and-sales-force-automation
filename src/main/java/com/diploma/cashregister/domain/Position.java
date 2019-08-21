package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "position")
@Data
@EqualsAndHashCode(of = "idPosition")
@NoArgsConstructor
public class Position {
    @Id
    @Column(name = "id_position")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long idPosition;
    private String name;
    private String description;

    @OneToMany(mappedBy = "position")
    private Collection<Contract> contracts;

}
