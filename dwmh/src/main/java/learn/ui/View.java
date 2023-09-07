package learn.ui;

import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public class View {
    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");

        MainMenuOption[] values = MainMenuOption.values();
        for (int i = 0; i < values.length; i++) {
            io.printf("%s. %s%n", i, values[i].getMessage());
        }
        String msg = String.format("Select [0-%s]: ", values.length - 1);
        int index = io.readInt(msg, 0, values.length - 1);
        io.println();
        return values[index];
    }
    public void printMessage(String message){
        io.println(message);
    }
    public String getHostEmail(){
        return io.readRequiredString("Host Email: ");
    }

    public String getGuestEmail(){
        return io.readRequiredString("Guest Email: ");
    }

    public void displayReservationsForHost(Host host, List<Reservation> reservations){
        io.println(host.getFirstName());
        io.println(host.getLocation().getAddress());
        io.println(host.getLocation().getCity() + ", " + host.getLocation().getState() + " " +
        host.getLocation().getPostalCode());

        for (Reservation reservation: reservations) {
            io.println("ID: " + reservation.getId() +
                       ", " + reservation.getStartDate() + " - " + reservation.getEndDate() +
                       ", Guest: " + reservation.getGuest().getLastName() + ", " + reservation.getGuest().getFirstName() +
                       ", Email: " + reservation.getGuest().getEmail());
        }
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("A critical error occurred:");
        io.println(ex.getMessage());
    }

}
