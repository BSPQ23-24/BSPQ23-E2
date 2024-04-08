package com.deusto.app.server.pojo;

public class BicycleData {
    private int id;
    private String acquisitionDate;
    private String type;
    private boolean isAvailable;
    private Integer stationId; // Assuming Station has an ID of type Integer

    public BicycleData() {
        // Required by serialization
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "BicycleData{" +
                "id=" + id +
                ", acquisitionDate='" + acquisitionDate + '\'' +
                ", type='" + type + '\'' +
                ", isAvailable=" + isAvailable +
                ", stationId=" + stationId +
                '}';
    }
}