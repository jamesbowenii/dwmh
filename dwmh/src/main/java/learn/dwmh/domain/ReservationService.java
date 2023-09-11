package learn.dwmh.domain;

import learn.dwmh.data.ReservationRepository;
import learn.dwmh.models.Guest;
import learn.dwmh.models.Host;
import learn.dwmh.models.Reservation;

import java.util.List;

public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public List<Reservation> viewReservationsForHost(Host host){
        return repository.viewReservationsForHost(host);
    }

    public List<Reservation> viewReservationsForHostAndGuest(Host host, Guest guest){
        return repository.viewReservationsForHostAndGuest(host, guest);
    }

    public Result<Reservation> addReservation(Reservation reservation){
            Result<Reservation> result = validate(reservation);

            if(result.isSuccess()){
                repository.addReservation(reservation);
            }

            return result;
    }

    public Result<Reservation> updateReservation(Reservation reservation){
        Result<Reservation> result = validate(reservation);
        int repoResult = -1;
        if(result.isSuccess()){
            repoResult = repository.updateReservation(reservation);
            if(repoResult <= 0){
                result.addErrorMessage("Something went wrong with the database.");
            }
        }
        return result;
    }

    public Result<Reservation> deleteReservation(Reservation reservation){
        Result<Reservation> result = new Result<>();
        if(repository.deleteReservation(reservation)){
            result.setPayload(reservation);
            return result;
        }else{
            result.addErrorMessage("Reservation could not be deleted.");
        }
        return result;
    }

    public Result<Reservation> validate(Reservation reservation){
        Result<Reservation> result = new Result<>();
        List<Reservation> reservations = viewReservationsForHost(reservation.getHost());

        if(reservation == null){
            result.addErrorMessage("Reservation cannot be null");
            return result;
        }

        if(reservation.getTotal() == null ){
            result.addErrorMessage("Reservation total cannot be null");
            return result;
        }

        for (Reservation reserv: reservations) {
            if(reserv.getId() != reservation.getId()) {
                if (reservation.getStartDate().isBefore(reserv.getEndDate()) && reservation.getStartDate().isAfter(reserv.getStartDate()) || reservation.getStartDate().equals(reserv.getStartDate())
                        || reservation.getEndDate().isBefore(reserv.getEndDate()) && reservation.getEndDate().isAfter(reserv.getStartDate())) {
                    result.addErrorMessage("Reservation dates conflict with an  existing reservation");
                    return result;
                }
            }
        }


        return result;
    }
}
