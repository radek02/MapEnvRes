package com.example.crudApp.models;

import com.example.crudApp.dtos.UserGroupDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usergroups")
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotBlank
    @Column(unique=true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "usergroups_users",
            joinColumns = @JoinColumn(name = "usergroup_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    public UserGroup(UserGroupDTO userGroupDto) {
        this.name = userGroupDto.getName();
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
