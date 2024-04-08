package com.deusto.app.server.pojo;

import java.util.List;

public class StationData {
    private int id;
    private String location;
    private List<Integer> bikeIds; // A list to hold bike IDs

    public StationData() {
        // Required by serialization
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Integer> getBikeIds() {
        return bikeIds;
    }

    public void setBikeIds(List<Integer> bikeIds) {
        this.bikeIds = bikeIds;
    }

    @Override
    public String toString() {
        return "StationData{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", bikeIds=" + bikeIds +
                '}';
    }
}

