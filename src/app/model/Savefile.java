package app.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Savefile {
    private StringProperty  name;
    private StringProperty date;


    @Override
    public String toString() {
        return "Savefile{" +
                "date=" + date +
                ", name=" + name +
                '}';
    }

    public Savefile(StringProperty date, StringProperty name) {
        this.date = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
    }

    public Savefile() {
        this.date = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
