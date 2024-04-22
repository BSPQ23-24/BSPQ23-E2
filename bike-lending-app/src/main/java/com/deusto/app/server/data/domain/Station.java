package com.deusto.app.server.data.domain;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class Station {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private int id;
    private String location;
    
    @Persistent(mappedBy = "station")
    private List<Bicycle> bikes;

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

    public List<Bicycle> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bicycle> bikes) {
        this.bikes = bikes;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("Station ID: ");
        result.append(this.id);
        result.append(" / Location: ");
        result.append(this.location);

        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.id == ((Station) obj).id;
        }

        return false;
    }

}
