package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "financial_operations")
@Data
@EqualsAndHashCode(of = "idFinancialOperations")
@NoArgsConstructor
public class FinancialOperations {
    @Id
    @Column(name = "id_financial_operations")
    private long idFinancialOperations;

    @Column(name = "time")
    private LocalDate time;

    @Column(name = "type")
    private String type;

    @Column(name = "comment")
    private String comment;

    @Column(name = "summ")
    private BigInteger summ;

    @ManyToOne
    @JoinColumn(name = "shift", referencedColumnName = "id_shift")
    private Shift shift;

}
