package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "worker")
@Data
@EqualsAndHashCode(of = "idWorker")
@NoArgsConstructor
public class Worker{
    @Id
    @Column(name = "id_worker")
    @SequenceGenerator(name="worker_id_worker_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="worker_id_worker_seq")
    private long idWorker;

    private String surname;
    @Column(name = "hire_date")
    private LocalDate hireDate;
    private String name;
    @Column(name = "date_of_birthday")
    private LocalDate dateOfBirthday;

    @OneToMany(mappedBy = "worker")
    private Collection<Contract> contracts;

    @OneToMany(mappedBy = "worker")
    private Collection<ShiftWorker> shiftWorkers;

    @OneToMany(mappedBy = "worker")
    private Collection<WorkerPassword> workerPasswords;

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "role", joinColumns = @JoinColumn(name = "id_worker"))
    @Enumerated(EnumType.STRING)
    @Getter
    private Set<Role> roles;

}
