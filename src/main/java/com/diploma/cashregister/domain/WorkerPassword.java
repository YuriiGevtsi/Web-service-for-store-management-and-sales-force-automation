package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "worker_password")
@Data
@EqualsAndHashCode(of = "idPassword")
@NoArgsConstructor
public class WorkerPassword {
    @Id
    @Column(name = "id_password")
    private int idPassword;

    private String password;

    @ManyToOne
    @JoinColumn(name = "worker", referencedColumnName = "id_worker")
    private Worker worker;

}
