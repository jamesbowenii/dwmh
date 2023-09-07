package learn.data;

import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    public List<Reservation> viewReservationsForHost(Host host);
}
