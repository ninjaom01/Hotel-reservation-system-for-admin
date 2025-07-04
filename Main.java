import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection con = DatabaseUtil.getConnection();
             Scanner sc = new Scanner(System.in)) {

            reservationservice service = new reservationservice(con);

            while (true) {
                System.out.println("\n Hotel Management System");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Logout");
                System.out.print("Choose an option: ");

                if (!sc.hasNextInt()) {
                    System.out.println(" Invalid input. Please enter a number.");
                    sc.next(); // skip non-integer
                    continue;
                }

                int choice = sc.nextInt();
                switch (choice) {
                    case 1 -> service.reserveRoom(sc);
                    case 2 -> service.viewReservations(sc);
                    case 3 -> service.getRoomNumber(sc);
                    case 4 -> service.updateReservation(sc);
                    case 5 -> service.deleteReservation(sc);
                    case 0 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println(" Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("DB Error: " + e.getMessage());
        }
    }
}