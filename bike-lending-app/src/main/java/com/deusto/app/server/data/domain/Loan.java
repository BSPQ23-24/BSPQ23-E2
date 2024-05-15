package com.deusto.app.server.data.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Loan class represents a loan transaction in the bike rental system.
 * It includes details such as loan ID, loan date, start hour, end hour,
 * associated bicycle, and the user who made the loan.
 */
@PersistenceCapable(detachable = "true")
public class Loan {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private int id;
    private String loanDate;
    private String startHour;
    private String endHour;

    @Persistent(defaultFetchGroup = "true")
    private Bicycle bicycle;

    @Persistent(defaultFetchGroup = "true")
    private User user;

    /**
     * Returns the ID of the loan.
     *
     * @return the loan ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the loan.
     *
     * @param id the loan ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the date of the loan.
     *
     * @return the loan date
     */
    public String getLoanDate() {
        return loanDate;
    }

    /**
     * Sets the date of the loan.
     *
     * @param loanDate the loan date to set
     */
    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Returns the start hour of the loan.
     *
     * @return the start hour
     */
    public String getStartHour() {
        return startHour;
    }

    /**
     * Sets the start hour of the loan.
     *
     * @param startHour the start hour to set
     */
    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    /**
     * Returns the end hour of the loan.
     *
     * @return the end hour
     */
    public String getEndHour() {
        return endHour;
    }

    /**
     * Sets the end hour of the loan.
     *
     * @param endHour the end hour to set
     */
    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    /**
     * Returns the bicycle associated with the loan.
     *
     * @return the bicycle
     */
    public Bicycle getBicycle() {
        return bicycle;
    }

    /**
     * Sets the bicycle associated with the loan.
     *
     * @param bicycle the bicycle to set
     */
    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    /**
     * Returns the user who made the loan.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made the loan.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns a string representation of the loan.
     *
     * @return a string containing the loan details
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Loan ID: ");
        result.append(this.id);
        result.append(" / Loan Date: ");
        result.append(this.loanDate);
        result.append(" / Start Hour: ");
        result.append(this.startHour);
        result.append(" / End Hour: ");
        result.append(this.endHour);
        result.append(" / Bicycle ID: ");
        result.append(this.bicycle.getId());
        result.append(" / User DNI: ");
        result.append(this.user.getDni());
        return result.toString();
    }

    /**
     * Checks if the given object is equal to this loan.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.id == ((Loan) obj).id;
        }
        return false;
    }
}