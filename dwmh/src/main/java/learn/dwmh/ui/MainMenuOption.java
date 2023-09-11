package learn.dwmh.ui;

public enum MainMenuOption {

    EXIT("Exit"),
    VIEW_RESERVATIONS_FOR_HOST("View Reservations for Host"),
    MAKE_A_RESERVATION("Make a Reservation"),
    EDIT_A_RESERVATION("Edit a Reservation"),
    CANCEL_A_RESERVATION("Cancel a Reservation");
    private String message;

    private MainMenuOption(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
