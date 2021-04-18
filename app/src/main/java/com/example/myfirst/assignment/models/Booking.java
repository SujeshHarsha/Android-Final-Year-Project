package com.example.myfirst.assignment.models;

public class Booking {
   String date,hotelId;

    public Booking(String date, String hotelId) {
        this.date = date;
        this.hotelId = hotelId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }
}
