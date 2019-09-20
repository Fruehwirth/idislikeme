package client.model;

import java.io.Serializable;

public class Event implements Serializable{
    String eventname;
    String avalibletickets;
    String price;

    public Event(String eventname, String avalibletickets, String price) {
        this.eventname = eventname;
        this.avalibletickets = avalibletickets;
        this.price = price;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getAvalibletickets() {
        return avalibletickets;
    }

    public void setAvalibletickets(String avalibletickets) {
        this.avalibletickets = avalibletickets;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Event{" + "eventname='" + eventname + '\'' + ", avalibletickets='" + avalibletickets + '\'' + ", price='" + price + '\'' + '}';
    }
}
