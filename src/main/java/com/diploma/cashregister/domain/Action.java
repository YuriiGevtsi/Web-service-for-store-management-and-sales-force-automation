package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "action")
@Data
@EqualsAndHashCode(of = "idAction")
@NoArgsConstructor
public class Action {
    @Id
    @Column(name = "idAction")
    private long idAction;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_finish")
    private Integer dateFinish;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
