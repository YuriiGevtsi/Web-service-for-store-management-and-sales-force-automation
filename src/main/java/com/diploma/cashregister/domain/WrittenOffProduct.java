package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "written_off_product")
@Data
@EqualsAndHashCode(of = "idPassword")
@NoArgsConstructor
public class WrittenOffProduct {
    @Id
    @Column(name = "id_written_off_product")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idWrittenOffProduct;

    private LocalDate date;
    private String reason;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product", nullable = false)
    private ProviderProduct providerProduct;

}
