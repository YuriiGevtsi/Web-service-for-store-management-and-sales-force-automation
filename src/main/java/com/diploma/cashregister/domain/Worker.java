package com.diploma.cashregister.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "worker")
@Data
@EqualsAndHashCode(of = "idWorker")
@NoArgsConstructor
@ToString(of = {"idWorker","name","surname","contact","dateOfBirthday"})
public class Worker{
    @Id
    @Column(name = "id_worker")
    @SequenceGenerator(name="worker_id_worker_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="worker_id_worker_seq")
    private long idWorker;

    private String surname;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    private String name;
    private String contact;
    @Column(name = "date_of_birthday")
    private LocalDate dateOfBirthday;

    @OneToMany(mappedBy = "worker", orphanRemoval = true)
    private Collection<Contract> contracts = new HashSet<>();

    @OneToMany(mappedBy = "worker", fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<ShiftWorker> shiftWorkers = new HashSet<>();

    @OneToMany(mappedBy = "worker", orphanRemoval = true)
    private Collection<WorkerPassword> workerPasswords = new HashSet<>();

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "id_worker"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "position", referencedColumnName = "id_position")
    private Position position;

    public void addRole(Role role) {
        roles.add(role);
    }
}
