package com.deusto.app.client.test;

import com.deusto.app.client.controller.UserController;
import com.deusto.app.server.pojo.UserData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class UserFunctionalityTest {

    private static UserController userController;

    @BeforeAll
    public static void setUp() {
        // Set the properties as needed for the test environment
        System.setProperty("bikeapp.hostname", "localhost");
        System.setProperty("bikeapp.port", "8080");
        userController = UserController.getInstance();
    }

    @Test
    public void testUserRegistration() {
        // Prepare user data
        UserData userData = new UserData();
        userData.setDni("12345678A");
        userData.setPassword("root");
        userData.setName("UsuarioTest");
        userData.setSurname("ApellidoTest");
        userData.setDateOfBirth("01-01-2000");
        userData.setPhone("123456789");
        userData.setMail("test@mail.es");

        // Attempt to register the user
        boolean registrationResult = userController.registerUser(userData);
        Assertions.assertTrue(registrationResult, "User registration should return true on success.");
    }
    
    @Test
    public void testUserLogin() {
        // Prepare user data
        UserData userData = new UserData();
        userData.setDni("12345678A");
        userData.setPassword("root");
        userData.setName("UsuarioTest");
        userData.setSurname("ApellidoTest");
        userData.setDateOfBirth("01-01-2000");
        userData.setPhone("123456789");
        userData.setMail("test@mail.es");


        // Attempt to log in the newly registered user
        Long token = userController.loginUser(userData);
        Assertions.assertNotNull(token, "Login should return a non-null token on success.");
    }
    
    @Test
    public void testChangePassword() {
        UserData userData = new UserData();
        userData.setDni("12345678A");
        
        // Attempt to change password
        String oldPassword = "root";
        String newPassword = "newPassword";
        boolean changePasswordResult = userController.changePassword(userData.getDni(), oldPassword, newPassword);
        Assertions.assertTrue(changePasswordResult, "Changing password should return true on success.");
    }

}
