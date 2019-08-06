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
public class Worker implements UserDetails {
    @Id
    @Column(name = "id_worker")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;//getRoles();
    }

    @Override
    public String getPassword() {
        return null;
    }

    public boolean isAdmin(){return roles.contains(Role.ADMIN);}

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
