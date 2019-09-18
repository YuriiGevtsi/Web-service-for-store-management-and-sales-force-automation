package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "shift")
@Data
@EqualsAndHashCode(of = "idShift")
@NoArgsConstructor
public class Shift {
    @Id
    @Column(name = "id_shift")
    @SequenceGenerator(name="shift_id_shift_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="shift_id_shift_seq")
    private long idShift;

    @Column(name = "beginning_time")
    private LocalDateTime beginningTime;

    @Column(name = "ending_time")
    private LocalDateTime endingTime;

    @OneToMany(mappedBy = "shift")
    private Collection<SellingOperation> sellingOperations = new HashSet<>();

    @OneToMany(mappedBy = "shift")
    private Collection<FinancialOperations> financialOperations = new HashSet<>();

    @OneToMany(mappedBy = "shift")
    private Collection<ShiftWorker> shiftWorkers = new HashSet<>();

}
