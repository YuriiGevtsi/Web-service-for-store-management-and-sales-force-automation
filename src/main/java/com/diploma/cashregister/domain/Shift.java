package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
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
    private int idShift;

    @Column(name = "beginning_time")
    private LocalDate beginningTime;

    @Column(name = "ending_time")
    private LocalDate endingTime;

    @OneToMany(mappedBy = "shift")
    private Collection<SellingOperation> sellingOperations;

    @OneToMany(mappedBy = "shift")
    private Collection<FinancialOperations> financialOperations;

    @OneToMany(mappedBy = "shift")
    private Collection<ShiftWorker> shiftWorkers;

}
