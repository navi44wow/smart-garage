package com.example.smartgarage.models.entities;


import com.example.smartgarage.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    public UserRoleEntity setRole(UserRole role){
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return role.name();
    }
}