package com.example.crudApp.services;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ApiResponseStatusDTO;
import com.example.crudApp.dtos.UserGroupDTO;
import com.example.crudApp.exceptions.UserNotFoundException;
import com.example.crudApp.models.User;
import com.example.crudApp.models.UserGroup;
import com.example.crudApp.repositories.UserGroupRepository;
import com.example.crudApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserGroupServiceImpl implements UserGroupService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<ApiResponseDTO<?>> addUserGroup(UserGroupDTO userGroupDTO)
    {
        try {
            UserGroup userGroup = new UserGroup(userGroupDTO);

            for(int userID : userGroupDTO.getUserIDs()) {
                Optional<User> user = userRepository.findByID(userID);

                if(!userRepository.findByID(userID).isPresent())
                    throw new UserNotFoundException();

                userGroup.addUser(user.get());
                user.get().addUserGroup(userGroup);
            } // TODO : not too clean

            userGroupRepository.save(userGroup);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(),
                            "New user group added successfully."));
        }
        catch(UserNotFoundException e) {
            // Using exception for auto rollback.
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), "User not found"));
        }
        catch(Exception e) {
            log.error("Exception during adding user group: {}", e.getMessage());
            throw e;
        }

    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getAllUserGroups()
    {
        try {
            List<UserGroupDTO> userGroupsDTOs = userGroupRepository.findAll().stream().map(UserGroupDTO::new).toList();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), userGroupsDTOs)
                    );
        }
        catch(Exception e) {
            log.error("Failed to fetch all user groups: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getUserGroupByID(int userGroupID)
    {
        try {
            UserGroup userGroup = userGroupRepository.findByID(userGroupID).orElseThrow();
            UserGroupDTO userGroupDTO = new UserGroupDTO(userGroup);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), userGroupDTO)
                    );
        }
        catch(Exception e) {
            log.error("Failed to fetch user group by ID: {}", e.getMessage());
            throw e;
        }
    }
}
