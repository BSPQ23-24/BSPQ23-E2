package com.deusto.app.unitary.domain;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.data.domain.User;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGetAndSetDni() {
        User user = new User();
        user.setDni("12345678A");
        assertEquals("12345678A", user.getDni());
    }

    @Test
    void testGetAndSetName() {
        User user = new User();
        user.setName("John");
        assertEquals("John", user.getName());
    }

    @Test
    void testGetAndSetSurname() {
        User user = new User();
        user.setSurname("Doe");
        assertEquals("Doe", user.getSurname());
    }

    @Test
    void testGetAndSetDateOfBirth() {
        User user = new User();
        user.setDateOfBirth("1990-01-01");
        assertEquals("1990-01-01", user.getDateOfBirth());
    }

    @Test
    void testGetAndSetPhone() {
        User user = new User();
        user.setPhone("123456789");
        assertEquals("123456789", user.getPhone());
    }

    @Test
    void testGetAndSetMail() {
        User user = new User();
        user.setMail("john.doe@example.com");
        assertEquals("john.doe@example.com", user.getMail());
    }

    @Test
    void testGetAndSetPassword() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testToString() {
        User user = new User();
        user.setDni("12345678A");
        user.setName("John");
        user.setPassword("password123");
        user.setSurname("Doe");
        user.setDateOfBirth("1990-01-01");
        user.setPhone("123456789");
        user.setMail("john.doe@example.com");
        String expected = "DNI: 12345678A / Password: password123 / Name: John / Surname: Doe / Date of Birth: 1990-01-01 / Phone: 123456789 / Email: john.doe@example.com / Admin: false";
        assertEquals(expected, user.toString());
    }

    @Test
    void testEquals() {
        User user1 = new User();
        User user2 = new User();
        user1.setDni("12345678A");
        user2.setDni("12345678A");
        assertTrue(user1.equals(user2));

        user2.setDni("87654321B");
        assertFalse(user1.equals(user2));

        assertFalse(user1.equals(new Object()));
    }
}
