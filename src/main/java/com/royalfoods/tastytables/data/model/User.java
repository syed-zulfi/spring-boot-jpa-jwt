package com.royalfoods.tastytables.data.model;

import lombok.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.*;

@Entity
@Table(name = "USERS")
@Data
public class User extends EntityAbstractModel implements UserDetails {
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")

    private String email;
    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(mappedBy = "users", cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = roles
                .stream()
                .map(e->new SimpleGrantedAuthority(e.getRoleName()))
                .collect(Collectors.toList());
        return authorities;

    }

    @Override
    public String getUsername() {
        return this.email;
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
