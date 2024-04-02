package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class Assign {
    @PrimaryKey
    private int ID;
    private Date assignDate;

    @Persistent(defaultFetchGroup="true")
    private Bicycle bicycle;

    @Persistent(defaultFetchGroup="true")
    private Station station;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Bicycle getBicycle() {
        return bicycle;
    }

    public void setBicycle(Bicycle bicycle) {
        this.bicycle = bicycle;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String toString() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YY", Locale.getDefault());

        StringBuilder result = new StringBuilder();

        result.append("Assignment ID: ");
        result.append(this.ID);
        result.append(" / Assigned Bicycle: ");
        result.append(this.bicycle.getID());
        result.append(" / Assigned Station: ");
        result.append(this.station.getID());
        result.append(" / Assignment Date: ");
        result.append(dateFormatter.format(this.assignDate));

        return result.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (this.getClass().getName().equals(obj.getClass().getName())) {
            return this.ID == ((Assign)obj).ID;
        }

        return false;
    }
}