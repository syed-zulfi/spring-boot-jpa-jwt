package com.royalfoods.tastytables.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ROLES")
@Data
@NoArgsConstructor
public class Role extends EntityAbstractModel{
    @Column(name = "ROLE_NAME")
    private String roleName;
    @Column(name = "ROLE_DESC")
    private String roleDesc;
    @ManyToMany
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "role_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id")
              )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<User> users = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }


}
