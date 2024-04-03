package com.deusto.app.server.data.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class Loan {
    @PrimaryKey
    private int ID;
    private Date loanDate;
    private String startHour;
    private String endHour;

    @Persistent(defaultFetchGroup="true")
    private Bicycle bicycle;

    @Persistent(defaultFetchGroup="true")
    private User user;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        StringBuilder result = new StringBuilder();

        result.append("Loan ID: ");
        result.append(this.ID);
        result.append(" / Loan Date: ");
        result.append(dateFormatter.format(this.loanDate));
        result.append(" / Start Hour: ");
        result.append(this.startHour);
        result.append(" / End Hour: ");
        result.append(this.endHour);
        result.append(" / Bicycle ID: ");
        result.append(this.bicycle.getID());
        result.append(" / User DNI: ");
        result.append(this.user.getDNI());

        return result.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.ID == ((Loan)obj).ID;
        }

        return false;
    }
}