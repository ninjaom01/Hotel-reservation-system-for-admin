import java.sql.Timestamp;

public class Reservation {
    private int id;
    private String guestName;
    private int roomNo;
    private String contact;
    private Timestamp reservationDate;


    
    public Reservation(int id, String guestName, int roomNo, String contact, Timestamp reservationDate) {
        this.id = id;
        this.guestName = guestName;
        this.roomNo = roomNo;
        this.contact = contact;
        this.reservationDate = reservationDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getGuestName() { return guestName; }
    public int getRoomNo() { return roomNo; }
    public String getContact() { return contact; }
    public Timestamp getReservationDate() { return reservationDate; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Room: %d | Contact: %s | Date: %s",
                id, guestName, roomNo, contact, reservationDate);
    }
}