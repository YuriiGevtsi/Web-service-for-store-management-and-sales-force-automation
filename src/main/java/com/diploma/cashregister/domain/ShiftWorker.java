package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "shift_worker")
@Data
@EqualsAndHashCode(of = "idShiftWorker")
@NoArgsConstructor
public class ShiftWorker {
    @Id
    @Column(name = "id_shift_worker")
    @SequenceGenerator(name="shift_worker_id_shift_worker_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="shift_worker_id_shift_worker_seq")
    private long idShiftWorker;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    @ManyToOne
    @JoinColumn(name = "worker", referencedColumnName = "id_worker")
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "shift", referencedColumnName = "id_shift")
    private Shift shift;

}
