package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "contract")
@Data
@EqualsAndHashCode(of = "idContract")
@NoArgsConstructor
public class Contract {
    @Id
    @Column(name = "id_contract")
    @SequenceGenerator(name="contract_id_contract_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="contract_id_contract_seq")
    private long idContract;

    @Column(name = "date_start")
    private LocalDate dateStart;
    @Column(name = "date_end")
    private LocalDate dateEnd;

    @ManyToOne
    @JoinColumn(name = "worker", referencedColumnName = "id_worker", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "position", referencedColumnName = "id_position", nullable = false)
    private Position position;

}
