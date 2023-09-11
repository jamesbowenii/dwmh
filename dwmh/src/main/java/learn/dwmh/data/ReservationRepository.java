package learn.dwmh.data;

import learn.dwmh.models.Guest;
import learn.dwmh.models.Host;
import learn.dwmh.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    public List<Reservation> viewReservationsForHost(Host host);
    public Reservation addReservation(Reservation reservation);

    public List<Reservation> viewReservationsForHostAndGuest(Host host, Guest guest);

    public int updateReservation(Reservation reservation);

    public boolean deleteReservation(Reservation reservation);
}
