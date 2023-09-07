package learn.domain;

import learn.data.ReservationRepository;
import learn.models.Host;
import learn.models.Reservation;

import java.util.List;

public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> viewReservationsForHost(Host host){
        return repository.viewReservationsForHost(host);
    }
}
