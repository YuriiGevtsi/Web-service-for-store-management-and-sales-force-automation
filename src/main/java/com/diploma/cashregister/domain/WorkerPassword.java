package com.diploma.cashregister.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "worker_password")
@Data
@EqualsAndHashCode(of = "idPassword")
@NoArgsConstructor
@ToString(of = {"idPassword","login","password"})
public class WorkerPassword  implements UserDetails{
    @Id
    @Column(name = "id_password")
    @SequenceGenerator(name="worker_password_id_password_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="worker_password_id_password_seq")
    private long idPassword;

    private String password;
    private String login;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "worker", referencedColumnName = "id_worker")
    private Worker worker;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return worker.getRoles();
    }

    @Override
    public String getUsername() {
        return String.valueOf(idPassword);
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
