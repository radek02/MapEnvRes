package com.example.crudApp.controllers;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.UserGroupDTO;
import com.example.crudApp.services.UserGroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usergroups")
public class UserGroupController {

    private UserGroupService userGroupService;

    @Autowired
    public UserGroupController(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<?>> addUserGroup(@Valid @RequestBody UserGroupDTO userGroupDto)
    {
        return userGroupService.addUserGroup(userGroupDto);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<?>> getAllUserGroups()
    {
        return userGroupService.getAllUserGroups();
    }

    @GetMapping("/by-id/{userGroupID}")
    public ResponseEntity<ApiResponseDTO<?>> getUserGroupByID(@PathVariable int userGroupID) {
        return userGroupService.getUserGroupByID(userGroupID);
    }
}
