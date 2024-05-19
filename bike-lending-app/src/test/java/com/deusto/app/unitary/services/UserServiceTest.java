package com.deusto.app.unitary.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.deusto.app.server.data.domain.User;
import com.deusto.app.server.pojo.UserData;
import com.deusto.app.server.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PersistenceManager pm;

    @Mock
    private Transaction tx;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser_Success() {
        UserData userData = new UserData();
        userData.setDni("12345678Z");
        userData.setPassword("password123");
        userData.setName("John");
        userData.setSurname("Doe");
        userData.setDateOfBirth("01-01-1980");
        userData.setPhone("555123456");
        userData.setMail("john@example.com");

        assertTrue(userService.registerUser(userData));
    }
    
    @Test
    public void testLoginUser_Success() {
        when(pm.getObjectById(User.class, "12345678A")).thenReturn(new User("12345678A", "password123", "John", "Doe", "01-01-1980", "555123456", "john@example.com", true));

        assertEquals(-1, userService.loginUser("12345678A", "wrongpassword"));
        assertNotEquals(-1, userService.loginUser("12345678A", "password123"));
    }
    
    @Test
    public void testLogoutUser_Success() {
        // Mock a valid user
        User user = new User("12345678Z", "password123", "John", "Doe", "01-01-1980", "555123456", "john@example.com", true);
        // Stub the getObjectById method to return the mock user
        when(pm.getObjectById(User.class, "12345678Z")).thenReturn(user);

        long token = userService.loginUser("12345678Z", "password123");

        assertNotNull(token);

        assertTrue(userService.logoutUser(token));
    }
    
    @Test
    public void testChangePassword_Success() {
        when(pm.getObjectById(User.class, "12345678A")).thenReturn(new User("12345678A", "password123", "John", "Doe", "01-01-1980", "555123456", "john@example.com", true));

        assertTrue(userService.changePassword("12345678A", "password123", "newpassword123"));
    }
    
    @Test
    public void testChangePassword_OldPasswordMismatch() {
        when(pm.getObjectById(User.class, "12345678A")).thenReturn(new User("12345678A", "password123", "John", "Doe", "01-01-1980", "555123456", "john@example.com", true));

        assertFalse(userService.changePassword("12345678A", "wrongpassword", "newpassword123"));
    }
    
}
