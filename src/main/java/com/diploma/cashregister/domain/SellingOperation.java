package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "selling_operation")
@Data
@EqualsAndHashCode(of = "idSelling")
@NoArgsConstructor
@ToString(of = {"idSelling","date","status","summ"})
public class SellingOperation {
    @Id
    @Column(name = "id_selling")
    @SequenceGenerator(name="selling_operation_id_selling_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="selling_operation_id_selling_seq")
    private long idSelling;

    private LocalDateTime date;
    private String status;
    private double summ;

    @OneToMany(mappedBy = "sellingOperation", orphanRemoval = true)
    private Collection<Bucket> buckets = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "client_card", referencedColumnName = "id_card")
    private ClientCard clientCard;

    @ManyToOne
    @JoinColumn(name = "shift", referencedColumnName = "id_shift")
    private Shift shift;

}
