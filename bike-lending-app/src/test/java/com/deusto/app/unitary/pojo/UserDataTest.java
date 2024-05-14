package com.deusto.app.unitary.pojo;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.pojo.UserData;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    @Test
    void testGetAndSetDni() {
        UserData userData = new UserData();
        userData.setDni("12345678A");
        assertEquals("12345678A", userData.getDni());
    }

    @Test
    void testGetAndSetName() {
        UserData userData = new UserData();
        userData.setName("John");
        assertEquals("John", userData.getName());
    }

    @Test
    void testGetAndSetSurname() {
        UserData userData = new UserData();
        userData.setSurname("Doe");
        assertEquals("Doe", userData.getSurname());
    }

    @Test
    void testGetAndSetDateOfBirth() {
        UserData userData = new UserData();
        userData.setDateOfBirth("1990-01-01");
        assertEquals("1990-01-01", userData.getDateOfBirth());
    }

    @Test
    void testGetAndSetPhone() {
        UserData userData = new UserData();
        userData.setPhone("123456789");
        assertEquals("123456789", userData.getPhone());
    }

    @Test
    void testGetAndSetMail() {
        UserData userData = new UserData();
        userData.setMail("john.doe@example.com");
        assertEquals("john.doe@example.com", userData.getMail());
    }

    @Test
    void testGetAndSetPassword() {
        UserData userData = new UserData();
        userData.setPassword("password123");
        assertEquals("password123", userData.getPassword());
    }

    @Test
    void testToString() {
        UserData userData = new UserData();
        userData.setDni("12345678A");
        userData.setName("John");
        userData.setPassword("password123");
        userData.setSurname("Doe");
        userData.setDateOfBirth("1990-01-01");
        userData.setPhone("123456789");
        userData.setMail("john.doe@example.com");
        String expected = "UserData [dni=12345678A, password=password123, name=John, surname=Doe, dateOfBirth=1990-01-01, phone=123456789, mail=john.doe@example.com]";
        assertEquals(expected, userData.toString());
    }
}
