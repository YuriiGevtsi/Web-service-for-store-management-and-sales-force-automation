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
@EqualsAndHashCode(of = "idShift")
@NoArgsConstructor
public class ShiftWorker {
    @Id
    @Column(name = "id_shift_worker")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idShiftWorker;

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
