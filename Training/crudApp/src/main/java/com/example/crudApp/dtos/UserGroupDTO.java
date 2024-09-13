package com.example.crudApp.dtos;

import com.example.crudApp.models.User;
import com.example.crudApp.models.UserGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserGroupDTO {
    private Integer ID = null;

    @NotBlank(message = "Name is mandatory")
    private final String name;

    @NotNull(message = "Users list is mandatory")
    private final List<Integer> userIDs;

    public UserGroupDTO(UserGroup userGroup)
    {
        this.ID = userGroup.getID();
        this.name = userGroup.getName();
        this.userIDs = userGroup.getUsers().stream().map(User::getID).toList();
    }
}
