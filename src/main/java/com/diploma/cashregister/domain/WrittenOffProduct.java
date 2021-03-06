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
@EqualsAndHashCode(of = "idWrittenOffProduct")
@NoArgsConstructor
public class WrittenOffProduct {
    @Id
    @Column(name = "id_written_off_product")
    @SequenceGenerator(name="written_off_product_id_written_off_product_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="written_off_product_id_written_off_product_seq")
    private long idWrittenOffProduct;

    private LocalDate date;
    private String reason;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product", nullable = false)
    private ProviderProduct providerProduct;

}
