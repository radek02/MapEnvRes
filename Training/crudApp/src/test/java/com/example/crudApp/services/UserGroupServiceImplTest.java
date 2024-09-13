package com.example.crudApp.services;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ApiResponseStatusDTO;
import com.example.crudApp.dtos.UserGroupDTO;
import com.example.crudApp.models.UserGroup;
import com.example.crudApp.repositories.UserGroupRepository;
import com.example.crudApp.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserGroupServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserGroupRepository userGroupRepository;

    @InjectMocks
    private UserGroupServiceImpl userGroupServiceImpl;

    // Custom:
    private final List<Integer> userIDs = List.of();
    private final UserGroupDTO userGroupDTO = new UserGroupDTO("TestGroup", userIDs);

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
         closeable = openMocks(this);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void addUserGroupSuccess() {
        ResponseEntity<ApiResponseDTO<?>> response = userGroupServiceImpl.addUserGroup(userGroupDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ApiResponseStatusDTO.SUCCESS.name(), response.getBody().getStatus());
        verify(userGroupRepository).save(any(UserGroup.class));
    }

    @Test
    void addUserGroupFailure() {
        when(userGroupRepository.save(any(UserGroup.class))).thenThrow(new DataIntegrityViolationException("msg"));

        assertThrows(Exception.class, () -> userGroupServiceImpl.addUserGroup(userGroupDTO));
    }
}
