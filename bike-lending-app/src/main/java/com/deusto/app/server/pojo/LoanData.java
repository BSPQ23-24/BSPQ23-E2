package com.deusto.app.server.pojo;

public class LoanData {
    private int id;
    private String loanDate;
    private String startHour;
    private String endHour;
    private Integer bicycleId; 
    private String userDni; 

    public LoanData() {
        // Required by serialization
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
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

    public Integer getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Integer bicycleId) {
        this.bicycleId = bicycleId;
    }

    public String getUserDni() {
        return userDni;
    }

    public void setUserDni(String userDni) {
        this.userDni = userDni;
    }

    @Override
    public String toString() {
        return "LoanData{" +
                "id=" + id +
                ", loanDate='" + loanDate + '\'' +
                ", startHour='" + startHour + '\'' +
                ", endHour='" + endHour + '\'' +
                ", bicycleId=" + bicycleId +
                ", userId=" + userDni +
                '}';
    }
}
