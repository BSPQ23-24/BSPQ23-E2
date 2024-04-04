package com.deusto.app.server.data.domain;

import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class Station {
    @PrimaryKey
    private int ID;
    private String location;
    private List<Bicycle> bicycles;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Bicycle> getBicycles() {
        return bicycles;
    }

    public void setBicycles(List<Bicycle> bicycles) {
        this.bicycles = bicycles;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Station ID: ");
        result.append(this.ID);
        result.append(" / Location: ");
        result.append(this.location);

        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.ID == ((Station)obj).ID;
        }

        return false;
    }
}
