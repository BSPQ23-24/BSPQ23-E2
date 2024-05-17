package com.deusto.app.unitary.domain;

import com.deusto.app.server.remote.ResourceFacade;
import com.deusto.app.server.services.UserService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceFacadeTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private ResourceFacade resourceFacade;

    @BeforeEach
    public void setUp() {
        // Reset mocks before each test if needed
        reset(userService);
    }

    @Test
    public void testChangePasswordSuccess() {
        String dni = "12345678X";
        String oldPassword = "oldPass";
        String newPassword = "newPass";
        when(userService.changePassword(dni, oldPassword, newPassword)).thenReturn(true);

        Response response = resourceFacade.changePassword(dni, oldPassword, newPassword);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(userService, times(1)).changePassword(dni, oldPassword, newPassword);
    }

    @Test
    public void testChangePasswordFailure() {
        String dni = "12345678X";
        String oldPassword = "oldPass";
        String newPassword = "newPass";
        when(userService.changePassword(dni, oldPassword, newPassword)).thenReturn(false);

        Response response = resourceFacade.changePassword(dni, oldPassword, newPassword);

        // Assert
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        verify(userService, times(1)).changePassword(dni, oldPassword, newPassword);
    }
}
