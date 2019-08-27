package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "action")
@Data
@EqualsAndHashCode(of = "idAction")
@NoArgsConstructor
public class Action {
    @Id
    @Column(name = "idAction")
    @SequenceGenerator(name="action_id_action_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="action_id_action_seq")
    private long idAction;

    @Column(name = "date_start")
    private LocalDateTime dateStart;

    @Column(name = "date_finish")
    private LocalDateTime dateFinish;

    @ManyToOne
    @JoinColumn(name = "provider_product", referencedColumnName = "id_provider_product")
    private ProviderProduct providerProduct;

}
