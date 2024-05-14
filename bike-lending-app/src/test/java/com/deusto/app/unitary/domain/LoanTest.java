package com.deusto.app.unitary.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.deusto.app.server.data.domain.Bicycle;
import com.deusto.app.server.data.domain.Loan;
import com.deusto.app.server.data.domain.User;

class LoanTest {

    @Test
    void testGetAndSetId() {
        Loan loan = new Loan();
        loan.setId(1);
        assertEquals(1, loan.getId());
    }

    @Test
    void testGetAndSetLoanDate() {
        Loan loan = new Loan();
        loan.setLoanDate("2021-05-14");
        assertEquals("2021-05-14", loan.getLoanDate());
    }

    @Test
    void testGetAndSetStartHour() {
        Loan loan = new Loan();
        loan.setStartHour("10:00");
        assertEquals("10:00", loan.getStartHour());
    }

    @Test
    void testGetAndSetEndHour() {
        Loan loan = new Loan();
        loan.setEndHour("12:00");
        assertEquals("12:00", loan.getEndHour());
    }

    @Test
    void testGetAndSetBicycle() {
        Loan loan = new Loan();
        Bicycle bicycle = new Bicycle();
        loan.setBicycle(bicycle);
        assertEquals(bicycle, loan.getBicycle());
    }

    @Test
    void testGetAndSetUser() {
        Loan loan = new Loan();
        User user = new User();
        loan.setUser(user);
        assertEquals(user, loan.getUser());
    }

    @Test
    void testToString() {
        Loan loan = new Loan();
        loan.setId(1);
        loan.setLoanDate("2021-05-14");
        loan.setStartHour("10:00");
        loan.setEndHour("12:00");

        Bicycle bicycle = new Bicycle();
        bicycle.setId(1);
        loan.setBicycle(bicycle);

        User user = new User();
        user.setDni("12345678A");
        loan.setUser(user);

        String expected = "Loan ID: 1 / Loan Date: 2021-05-14 / Start Hour: 10:00 / End Hour: 12:00 / Bicycle ID: 1 / User DNI: 12345678A";
        assertEquals(expected, loan.toString());
    }

    @Test
    void testEquals() {
        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        loan1.setId(1);
        loan2.setId(1);
        assertTrue(loan1.equals(loan2));

        loan2.setId(2);
        assertFalse(loan1.equals(loan2));

        assertFalse(loan1.equals(new Object()));
    }
}
