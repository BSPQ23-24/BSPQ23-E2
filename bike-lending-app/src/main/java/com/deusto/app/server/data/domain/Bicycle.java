package com.deusto.app.server.data.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Bicycle class represents a bicycle in the bike rental system.
 * It includes details such as ID, acquisition date, type, availability status,
 * and its associated station.
 */
@PersistenceCapable(detachable = "true")
public class Bicycle {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
    private int id;
    private String acquisitionDate;
    private String type;
    private boolean isAvailable;
    
    @Persistent(defaultFetchGroup = "true")
    private Station station;

    /**
     * Returns the station associated with the bicycle.
     *
     * @return the station
     */
    public Station getStation() {
        return station;
    }

    /**
     * Sets the station associated with the bicycle.
     *
     * @param station the station to set
     */
    public void setStation(Station station) {
        this.station = station;
    }

    /**
     * Returns the ID of the bicycle.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the bicycle.
     *
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the acquisition date of the bicycle.
     *
     * @return the acquisition date
     */
    public String getAcquisitionDate() {
        return acquisitionDate;
    }

    /**
     * Sets the acquisition date of the bicycle.
     *
     * @param acquisitionDate the acquisition date to set
     */
    public void setAcquisitionDate(String acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }

    /**
     * Returns the type of the bicycle.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the bicycle.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the availability status of the bicycle.
     *
     * @return true if the bicycle is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the bicycle.
     *
     * @param available the availability status to set
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Returns a string representation of the bicycle.
     *
     * @return a string containing the bicycle details
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("ID: ");
        result.append(this.id);
        result.append(" / Type: ");
        result.append(this.type);
        result.append(" / Acquisition Date: ");
        result.append(this.acquisitionDate);
        result.append(" / Available: ");
        result.append(this.isAvailable ? "Yes" : "No");
        return result.toString();
    }

    /**
     * Checks if the given object is equal to this bicycle.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.id == ((Bicycle) obj).id;
        }
        return false;
    }
}
