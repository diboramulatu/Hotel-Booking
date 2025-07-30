package model;

public class Room {
    private int roomId;
    private String roomType;
    private double price;
    private String roomNumber;
    
    public Room(int roomId, String roomType, double price,String roomNumber) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.roomNumber = roomNumber;
    }

    // public void book() {
    //     isAvailable = false;
    // }

    // public void release() {
    //     isAvailable = true;
    // }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - $" + price ;
    }

    public int getRoomId() {
        return roomId;
    }
    public String getRoomNumber(){
        return roomNumber;
    }
    // public boolean isAvailable() {
    //     return isAvailable;
    // }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    // public void setAvailable(boolean available) {
    //     isAvailable = available;
    // }

    public void setPrice(double price) {
        this.price = price;
    }
}
