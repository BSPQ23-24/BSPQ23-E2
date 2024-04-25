package com.deusto.app.server.data.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class Bicycle {
<<<<<<< Updated upstream
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)  // Tampoco se si es necesario o hace la incrementacion sola
    private int id;
    private Date acquisitionDate;
    private String type;
    private boolean isAvailable;
    @Persistent(defaultFetchGroup="true")
    private Station station; // Relación con la estación
=======
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT) // Tampoco se si es necesario o hace la incrementacion															// sola
	private int id;
	private String acquisitionDate;
	private String type;
	private boolean isAvailable;
	@Persistent(defaultFetchGroup = "true")
	private Station station; // Relación con la estación
>>>>>>> Stashed changes


    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Date acquisitionDate) {
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


    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YY", Locale.getDefault());

        StringBuilder result = new StringBuilder();

        result.append("ID: ");
        result.append(this.id);
        result.append(" / Type: ");
        result.append(this.type);
        result.append(" / Acquisition Date: ");
        result.append(dateFormatter.format(this.acquisitionDate));
        result.append(" / Available: ");
        result.append(this.isAvailable ? "Yes" : "No");

        return result.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.id == ((Bicycle)obj).id;
        }

        return false;
    }
}