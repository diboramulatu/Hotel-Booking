package model;

public class DoubleRoom extends Room {
    public DoubleRoom(int roomId, double price, String roomNumber) {
        super(roomId, "Double", price, roomNumber);
    }

    // @Override
    // public void book() {
    //     //super.book();
    //     System.out.println("Double room booked.");
    // }
}
