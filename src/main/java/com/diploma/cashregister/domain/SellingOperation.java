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
    @SequenceGenerator(name="selling_operation_id_selling_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="selling_operation_id_selling_seq")
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
