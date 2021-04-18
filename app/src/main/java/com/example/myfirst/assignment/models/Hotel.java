package com.example.myfirst.assignment.models;

public class Hotel {
    String id,hotelName,roomNo,phone,description,noOfBed,address,roomImage;

    public Hotel(String id, String hotelName, String roomNo, String phone, String description, String noOfBed, String address, String roomImage) {
        this.id = id;
        this.hotelName = hotelName;
        this.roomNo = roomNo;
        this.phone = phone;
        this.description = description;
        this.noOfBed = noOfBed;
        this.address = address;
        this.roomImage = roomImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoOfBed() {
        return noOfBed;
    }

    public void setNoOfBed(String noOfBed) {
        this.noOfBed = noOfBed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }
}
