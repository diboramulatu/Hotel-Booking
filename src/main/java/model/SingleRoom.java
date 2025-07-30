package model;

public class SingleRoom extends Room {
    public SingleRoom(int roomId, double price, String roomNumber) {
        super(roomId, "Single", price, roomNumber);
    }

    // @Override
    // public void book() {
    //     //super.book();
    //     System.out.println("Single room booked.");
    // }
}
