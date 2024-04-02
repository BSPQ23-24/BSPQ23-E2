package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable="true")
public class Bicycle {
    @PrimaryKey
    private int ID;
    private Date acquisitionDate;
    private String type;
    private boolean isAvailable;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
        result.append(this.ID);
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
            return this.ID == ((Bicycle)obj).ID;
        }

        return false;
    }
}