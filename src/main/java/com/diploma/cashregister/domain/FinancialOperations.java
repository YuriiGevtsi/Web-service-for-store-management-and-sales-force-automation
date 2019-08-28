package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "financial_operations")
@Data
@EqualsAndHashCode(of = "idFinancialOperations")
@NoArgsConstructor
public class FinancialOperations {
    @Id
    @Column(name = "id_financial_operations")
    @SequenceGenerator(name="financial_operations_id_financial_operations_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="financial_operations_id_financial_operations_seq")
    private long idFinancialOperations;

    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "type")
    private String type;

    @Column(name = "comment")
    private String comment;

    @Column(name = "summ")
    private float summ;

    @ManyToOne
    @JoinColumn(name = "shift", referencedColumnName = "id_shift")
    private Shift shift;

}
