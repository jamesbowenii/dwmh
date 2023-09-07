package learn.ui;

import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public class Controller {

    private final View view;
    private final HostService hostService;

    private final ReservationService reservationService;
    public Controller(View view, HostService hostService, ReservationService reservationService) {
        this.view = view;
        this.hostService = hostService;
        this.reservationService = reservationService;
    }


    public void run() {
        view.displayHeader("Welcome to DWMH");
        try {
            runAppLoop();
        } catch (Exception ex) {
            view.displayException(ex);
        }
        view.displayHeader("Goodbye.");
    }

    private void runAppLoop() {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case VIEW_RESERVATIONS_FOR_HOST:
                    viewReservationsForHost();
                    break;
                case MAKE_A_RESERVATION:
                    addReservation();
                    break;
                case EDIT_A_RESERVATION:
                    updateReservation();
                    break;
                case CANCEL_A_RESERVATION:
                    deleteReservation();
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void viewReservationsForHost(){
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email);
        if(host != null) {
            List<Reservation> reservations = reservationService.viewReservationsForHost(host);
            if(reservations.size() > 0) {
                view.displayReservationsForHost(host, reservations);
            }else {
                view.printMessage("This host has no reservations.");
            }
        }else{
            view.printMessage("Host could not be found. Please try again!");
        }
    }

    private void addReservation(){
        view.displayHeader(MainMenuOption.MAKE_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();
    }

    private void updateReservation(){

    }

    private void deleteReservation(){

    }
}
