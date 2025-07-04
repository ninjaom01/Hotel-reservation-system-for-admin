import java.sql.*;
import java.util.Scanner;

public class reservationservice {
    private final Connection con;

    public reservationservice(Connection con) {
        this.con = con;
    }

    public void reserveRoom(Scanner sc)   {

        try {
            System.out.print("Enter guest name: ");
            String guest_Name = sc.next();

            System.out.print("Enter room number: ");
            int room_No = sc.nextInt();

            System.out.print("Enter contact number: ");
            String contact = sc.next();

            String sql = "INSERT INTO reservation (guest_name, roon_no, contact_no, reservation_date) " +
                    "VALUES (?, ?, ?, CURRENT_DATE)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, guest_Name);
                ps.setInt(2, room_No);
                ps.setString(3, contact);

                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? "Reservation successful!" : "Reservation failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error while reserving: " + e.getMessage());
        }
    }
    public void viewReservations(Scanner sc) {
        String sql = "SELECT * FROM reservation";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n Current Reservations:");
            System.out.println("+----------------+-------------+---------+--------------+---------------------+");
            System.out.printf("| %-14s | %-11s | %-7s | %-12s | %-19s |\n",
                    "Reservation ID", "Guest Name", "Room No", "Contact No", "Reservation Time");
            System.out.println("+----------------+-------------+---------+--------------+---------------------+");

            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String name = rs.getString("guest_name");
                int room = rs.getInt("roon_no");
                String contact = rs.getString("contact_no");
                Timestamp time = rs.getTimestamp("reservation_date");

                System.out.printf("| %-14d | %-11s | %-7d | %-12s | %-19s |\n",
                        id, name, room, contact, time);
            }

            System.out.println("+----------------+-------------+---------+--------------+---------------------+");

        } catch (SQLException e) {
            System.out.println("Error retrieving reservations: " + e.getMessage());
        }
    }

    public void updateReservation( Scanner sc)  {
        try {
        System.out.print("Enter reservation ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new guest name: ");
        String guest = sc.nextLine();

        System.out.print("Enter new contact number: ");
        String contact = sc.nextLine();

        System.out.print("Enter new room number: ");
        int room = sc.nextInt();

        String sql = "UPDATE reservation SET guest_name = ?, contact_no= ?, roon_no = ? WHERE reservation_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, guest);
            ps.setString(2, contact);
            ps.setInt(3, room);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Reservation updated." : "No reservation found.");
        }

    } catch (SQLException e) {
        System.out.println("Error updating reservation: " + e.getMessage());
    }
}

    public void deleteReservation(Scanner sc) {
        try {
            System.out.print("Enter reservation ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM reservation WHERE reservation_id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rows = ps.executeUpdate();
                System.out.println(rows > 0 ? " Reservation deleted." : "No reservation found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting reservation: " + e.getMessage());
        }
    }

    public void getRoomNumber( Scanner sc) {
        try {
            System.out.print("Enter reservation ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter guest name: ");
            String guestName = sc.next();

            String sql = "SELECT room_no FROM reservation WHERE reservation_id = ? AND guest_name = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, guestName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int roomNo = rs.getInt("room_no");
                        System.out.println(" Room Number: " + roomNo);
                    } else {
                        System.out.println(" No matching reservation found.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching room number: " + e.getMessage());
        }
    }
}