package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "shift")
@Data
@EqualsAndHashCode(of = "idShift")
@NoArgsConstructor
public class Shift {
    @Id
    @Column(name = "id_shift")
    @SequenceGenerator(name="shift_id_shift_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="shift_id_shift_seq")
    private int idShift;

    @Column(name = "beginning_time")
    private LocalDateTime beginningTime;

    @Column(name = "ending_time")
    private LocalDateTime endingTime;

    @OneToMany(mappedBy = "shift")
    private Collection<SellingOperation> sellingOperations;

    @OneToMany(mappedBy = "shift")
    private Collection<FinancialOperations> financialOperations;

    @OneToMany(mappedBy = "shift")
    private Collection<ShiftWorker> shiftWorkers;

}
