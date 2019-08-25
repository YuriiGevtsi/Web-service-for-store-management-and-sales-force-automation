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
@Table(name = "selling_operation")
@Data
@EqualsAndHashCode(of = "idSelling")
@NoArgsConstructor
public class SellingOperation {
    @Id
    @Column(name = "id_selling")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long idSelling;

    private LocalDateTime date;
    private String status;
    private double summ;

    @OneToMany(mappedBy = "sellingOperation")
    private Collection<Bucket> buckets;

    @ManyToOne
    @JoinColumn(name = "client_card", referencedColumnName = "id_card")
    private ClientCard clientCard;

    @ManyToOne
    @JoinColumn(name = "shift", referencedColumnName = "id_shift")
    private Shift shift;

}
