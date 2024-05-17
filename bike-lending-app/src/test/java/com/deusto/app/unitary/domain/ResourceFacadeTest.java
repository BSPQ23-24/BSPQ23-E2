package com.deusto.app.unitary.domain;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.deusto.app.server.remote.ResourceFacade;
import com.deusto.app.server.services.AdminService;
import com.deusto.app.server.services.BikeService;
import com.deusto.app.server.services.UserService;

import jakarta.ws.rs.core.Response;

public class ResourceFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private BikeService bikeService;

    @Mock
    private AdminService adminService;

    @InjectMocks
    private ResourceFacade resourceFacade;
    
    @Before
    public void setUp() {
     MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testChangePasswordSuccess() {
        when(userService.changePassword("12345678A", "oldPass", "newPass")).thenReturn(true);

        Response response = resourceFacade.changePassword("12345678A", "oldPass", "newPass");

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

}
