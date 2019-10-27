package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "price")
@Data
@EqualsAndHashCode(of = "idPrice")
@NoArgsConstructor
@ToString(of = {"idPrice","price","dateStart","dateFinish"})
public class Price {
    @Id
    @Column(name = "id_price")
    @SequenceGenerator(name="price_id_price_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="price_id_price_seq")
    private long idPrice;

    private double price;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_finish")
    private LocalDate dateFinish;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
