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
    @SequenceGenerator(name="position_id_position_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="position_id_position_seq")
    private long idPosition;
    private String name;
    private String description;

    @OneToMany(mappedBy = "position")
    private Collection<Contract> contracts;

}
