package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "shift_schedule")
@Data
@EqualsAndHashCode(of = "idShiftSchedule")
@NoArgsConstructor
public class ShiftSchedule {
    @Id
    @Column(name = "id_shift_schedule")
    @SequenceGenerator(name="shift_schedule_id_shift_schedule_seq2", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="shift_schedule_id_shift_schedule_seq2")
    private long idShiftSchedule;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "shift_time")
    private String shiftTime;

    @ManyToOne
    @JoinColumn(name = "worker", referencedColumnName = "id_worker")
    private Worker worker;
}
