package learn.dwmh.ui;

import learn.dwmh.models.Host;
import learn.dwmh.models.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public LocalDate getStartDate(){
        boolean correctDate = false;
        LocalDate startDate;
        do {
            startDate = io.readLocalDate("Start Date (MM/dd/yyyy): ");
            if(startDate.isAfter(LocalDate.now())){
                correctDate = true;
            }else{
                printMessage("Start date must be in the future!");
            }
        }while(!correctDate);

        return startDate;
    }

    public LocalDate updateStartDate(Reservation reservation){
        boolean correctDate = false;
        LocalDate startDate = reservation.getStartDate();
        LocalDate newStartDate;
        do {
            newStartDate = io.editLocalDate("Start Date (" + reservation.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/YYY")) + "): ");
            if(!newStartDate.isEqual(LocalDate.of(1111,11,11))){
            if(newStartDate.isAfter(LocalDate.now())){
                startDate = newStartDate;
                correctDate = true;
            }else{
                printMessage("Start date must be in the future!");
            }
        } else{
                correctDate = true;
            }
        }while(!correctDate);

        return startDate;
    }

    public LocalDate updateEndDate(Reservation reservation){
        boolean correctDate = false;
        LocalDate endDate = reservation.getEndDate();
        LocalDate newEndDate;
        do {
            newEndDate = io.editLocalDate("End Date (" +reservation.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/YYY")) + "): ");
            if(!newEndDate.isEqual(LocalDate.of(1111,11,11))) {
                if (endDate.isAfter(reservation.getStartDate())) {
                    endDate = newEndDate;
                    correctDate = true;
                } else {
                    printMessage("End date must be after start date!");
                }
            }else{
                correctDate = true;
            }
        }while(!correctDate);
        return endDate;
    }
    public LocalDate getEndDate(LocalDate startDate){

        boolean correctDate = false;
        LocalDate endDate;
        do {
            endDate = io.readLocalDate("End Date (MM/dd/yyyy): ");
            if(endDate.isAfter(startDate)){
                correctDate = true;
            }else{
                printMessage("End date must be after start date!");
            }
        }while(!correctDate);
        return endDate;
    }


    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public boolean confirmReservation(Reservation reservation){
        displayHeader("Summary");
        printMessage("Start: " + reservation.getStartDate());
        printMessage("End: " + reservation.getEndDate());
        printMessage("Total: " + reservation.getTotal());
       return io.readBoolean("Is this okay? [y/n]: ");

    }
    public void displayReservationsForHost(Host host, List<Reservation> reservations){
        io.println();
        io.println(host.getFirstName());
        io.println(host.getLocation().getAddress());
        String address = host.getLocation().getCity() + ", " + host.getLocation().getState() + " " +
        host.getLocation().getPostalCode();
        io.println(address);
        io.println("=".repeat(address.length()));


        for (Reservation reservation: reservations) {
            io.println("ID: " + reservation.getId() +
                       ", " + reservation.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/YYY")) + " - " + reservation.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) +
                       ", Guest: " + reservation.getGuest().getLastName() + ", " + reservation.getGuest().getFirstName() +
                       ", Email: " + reservation.getGuest().getEmail());
        }
    }

    public Reservation displayReservationsForHostAndReturnReservationToEdit(Host host, List<Reservation> reservations){
        Reservation reservationReturning = new Reservation();
        io.println();
        io.println(host.getFirstName());
        io.println(host.getLocation().getAddress());
        String address = host.getLocation().getCity() + ", " + host.getLocation().getState() + " " +
                host.getLocation().getPostalCode();
        io.println(address);
        io.println("=".repeat(address.length()));

        int reservationId = 0;
        do {
            for (Reservation reservation : reservations) {
                io.println("ID: " + reservation.getId() +
                        ", " + reservation.getStartDate().format(DateTimeFormatter.ofPattern("MM/dd/YYY")) + " - " + reservation.getEndDate().format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) +
                        ", Guest: " + reservation.getGuest().getLastName() + ", " + reservation.getGuest().getFirstName() +
                        ", Email: " + reservation.getGuest().getEmail());
            }
            reservationId = io.readInt("Reservation ID: ");

            for (Reservation reservation: reservations) {
                if(reservationId == reservation.getId()){
                    reservationReturning = reservation;
                    break;
                }
            }
        }while(reservationId <= 0 || reservationReturning.getId() == 0);

        return reservationReturning;
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
