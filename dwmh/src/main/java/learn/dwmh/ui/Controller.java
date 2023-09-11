package learn.dwmh.ui;

import learn.dwmh.domain.GuestService;
import learn.dwmh.domain.HostService;
import learn.dwmh.domain.ReservationService;
import learn.dwmh.domain.Result;
import learn.dwmh.models.Guest;
import learn.dwmh.models.Host;
import learn.dwmh.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Controller {

    private final View view;
    private final HostService hostService;

    private final ReservationService reservationService;

    private final GuestService guestService;

    public Controller(View view, HostService hostService, ReservationService reservationService, GuestService guestService) {
        this.view = view;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.guestService = guestService;
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

    private void viewReservationsForHost() {
        view.displayHeader(MainMenuOption.VIEW_RESERVATIONS_FOR_HOST.getMessage());
        String email = view.getHostEmail();
        Host host = hostService.findByEmail(email);
        confirmHost(host);
    }

    private void addReservation() {
        view.displayHeader(MainMenuOption.MAKE_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        Guest guest = guestService.findByEmail(guestEmail);
        confirmGuestAndHostAdding(host, guest);
        Reservation reservation = new Reservation();
        reservation.setHost(host);
        reservation.setStartDate(view.getStartDate());
        reservation.setEndDate(view.getEndDate(reservation.getStartDate()));
        reservation.setLocation(host.getLocation());
        reservation.setGuest(guest);
        BigDecimal total = reservation.calculateTotal(reservation.getHost().getLocation());
        reservation.setTotal(total);
        confirmReservation(reservation);
    }

    private void updateReservation() {
        view.displayHeader(MainMenuOption.EDIT_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        Guest guest = guestService.findByEmail(guestEmail);
        Reservation reservation = confirmGuestAndHostEditing(host, guest);
        if(reservation.getId() != 0) {
            view.displayHeader("Editing Reservation " + reservation.getId());
            reservation.setStartDate(view.updateStartDate(reservation));
            reservation.setEndDate(view.updateEndDate(reservation));
            reservation.setTotal(reservation.calculateTotal(host.getLocation()));
            reservation.setHost(host);
            if(view.confirmReservation(reservation)){
                Result<Reservation> result = reservationService.updateReservation(reservation);
                result.setPayload(reservation);
                if(result.isSuccess()) {
                    String successMessage = String.format("Reservation %s updated.", result.getPayload().getId());
                    view.displayStatus(true, Collections.singletonList(successMessage));
                }else{
                    view.displayStatus(false, result.getErrorMessages());
                }
            }
        }

    }

    private void deleteReservation() {
        view.displayHeader(MainMenuOption.CANCEL_A_RESERVATION.getMessage());
        String guestEmail = view.getGuestEmail();
        String hostEmail = view.getHostEmail();
        Host host = hostService.findByEmail(hostEmail);
        Guest guest = guestService.findByEmail(guestEmail);
        Reservation reservation = confirmGuestAndHostEditing(host, guest);
        if(reservation.getId() != 0) {
            Result<Reservation> result = reservationService.deleteReservation(reservation);

            if(result.isSuccess()) {
                String successMessage = String.format("Reservation %s cancelled.", result.getPayload().getId());
                view.displayStatus(true, Collections.singletonList(successMessage));
            }else{
                view.displayStatus(false, result.getErrorMessages());
            }
        }else{
            view.printMessage("Incorrect ID selected.");
        }
    }

    private void confirmHost(Host host){
        if (host != null) {
            List<Reservation> reservations = reservationService.viewReservationsForHost(host);
            if (reservations.size() > 0) {
                view.displayReservationsForHost(host, reservations);
            } else {
                view.printMessage("This host has no reservations.");
            }
        } else {
            view.printMessage("Host could not be found. Please try again!");
        }

    }

    private void confirmGuestAndHostAdding(Host host, Guest guest){
        Reservation reservation = new Reservation();
        if (host != null || guest != null) {
            List<Reservation> reservations = reservationService.viewReservationsForHost(host);
            if (reservations.size() > 0) {
                view.displayReservationsForHost(host, reservations);
            } else {
                view.printMessage("This host has no reservations.");
            }

        } else {
            if (host == null) {
                view.printMessage("Host could not be found. Please try again!");
            } else if (guest == null) {
                view.printMessage("Guest could not be found. Please Try again!");
            }
        }
    }

    private Reservation confirmGuestAndHostEditing(Host host, Guest guest){
        Reservation reservation = new Reservation();
        if (host != null || guest != null) {
            List<Reservation> reservations = reservationService.viewReservationsForHostAndGuest(host, guest);
            if (reservations.size() > 0) {
                reservation = view.displayReservationsForHostAndReturnReservationToEdit(host, reservations);
                if(reservation == null){
                    view.printMessage("There is no reservation that matches these credentials.");
                }
            } else {
                view.printMessage("This host has no reservations.");
            }
        } else {
            if (host == null) {
                view.printMessage("Host could not be found. Please try again!");
            } else if (guest == null) {
                view.printMessage("Guest could not be found. Please Try again!");
            }
        }
        return reservation;
    }

    private void confirmReservation(Reservation reservation){
        if (view.confirmReservation(reservation)) {
            Result<Reservation> result = reservationService.addReservation(reservation);
            result.setPayload(reservation);
            if(result.isSuccess()) {
                String successMessage = String.format("Reservation %s created.", result.getPayload().getId());
                view.displayStatus(true, Collections.singletonList(successMessage));
            }else{
                view.displayStatus(false, result.getErrorMessages());
            }
        }
    }
}


