package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "position")
@Data
@EqualsAndHashCode(of = "idPosition")
@NoArgsConstructor
public class Position {
    @Id
    @Column(name = "id_position")
    @SequenceGenerator(name="position_id_position_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="position_id_position_seq")
    private long idPosition;
    private String name;
    private String description;

    @OneToMany(mappedBy = "position", orphanRemoval = true)
    private Collection<Contract> contracts = new HashSet<>();

    @OneToMany(mappedBy = "position", orphanRemoval = true)
    private Collection<Worker> workers = new HashSet<>();

}
