package com.deusto.app.server.data.domain;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Station class represents a bike station in the bike rental system.
 * It includes details such as station ID, location, and the list of bikes associated with the station.
 */
@PersistenceCapable(detachable = "true")
public class Station {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private int id;
    private String location;

    @Persistent(mappedBy = "station")
    private List<Bicycle> bikes;

    /**
     * Returns the ID of the station.
     *
     * @return the station ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the station.
     *
     * @param id the station ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the location of the station.
     *
     * @return the station location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the station.
     *
     * @param location the station location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the list of bikes associated with the station.
     *
     * @return the list of bikes
     */
    public List<Bicycle> getBikes() {
        return bikes;
    }

    /**
     * Sets the list of bikes associated with the station.
     *
     * @param bikes the list of bikes to set
     */
    public void setBikes(List<Bicycle> bikes) {
        this.bikes = bikes;
    }

    /**
     * Returns a string representation of the station.
     *
     * @return a string containing the station details
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Station ID: ");
        result.append(this.id);
        result.append(" / Location: ");
        result.append(this.location);
        return result.toString();
    }

    /**
     * Checks if the given object is equal to this station.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.id == ((Station) obj).id;
        }
        return false;
    }
}
