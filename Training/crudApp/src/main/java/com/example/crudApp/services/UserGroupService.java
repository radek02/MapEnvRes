package com.example.crudApp.services;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.UserGroupDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupService {

    ResponseEntity<ApiResponseDTO<?>> addUserGroup(UserGroupDTO userGroupDto);

    ResponseEntity<ApiResponseDTO<?>> getAllUserGroups();

    ResponseEntity<ApiResponseDTO<?>> getUserGroupByID(int userGroupID);
}
