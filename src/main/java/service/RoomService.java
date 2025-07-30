package service;

import dao.RoomDAO;
import exception.ServiceException;
import model.Room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class RoomService {
    private final RoomDAO roomDAO;
    private final Map<Integer, Room> cache = new HashMap<>();

    public RoomService(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    public List<Room> getAvailableRooms(LocalDate checkIn , LocalDate checkOut) throws ServiceException {
        try {
            List<Room> rooms = roomDAO.getAllAvailableRooms(checkIn, checkOut);
            cache.clear();
            for (Room r : rooms) {
                cache.put(r.getRoomId(), r); // assumes Room has getRoomId()
            }
            return rooms;
        } catch (Exception e) {
            throw new ServiceException("Failed to fetch available rooms", e);
        }
    }

    public Room getRoomById(int id) throws ServiceException {
        try {
           return cache.containsKey(id) ? cache.get(id) : roomDAO.getRoomById(id);
        } catch (Exception e) {
            throw new ServiceException("Failed to get room by ID: " + id, e);
      }
    }

    // public boolean isRoomAvailable(int id) throws ServiceException {
    //     Room room = getRoomById(id);
    //     return room != null && room.isAvailable(); // requires isAvailable() in Room
    // }  

    public void markBooked(int id) throws ServiceException {
        try {
            roomDAO.updateRoomAvailability(id, false);
            cache.remove(id); // refresh on next call
        } catch (Exception e) {
            throw new ServiceException("Failed to mark room as booked", e);
        }
    }

    public void markReleased(int id) throws ServiceException {
     try {
            roomDAO.updateRoomAvailability(id, true);
        } catch (Exception e) {
            throw new ServiceException("Failed to mark room as released", e);
        }
    }
    
}