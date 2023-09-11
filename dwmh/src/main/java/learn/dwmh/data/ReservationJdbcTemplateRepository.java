package learn.dwmh.data;

import learn.dwmh.models.Guest;
import learn.dwmh.models.Host;
import learn.dwmh.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;

public class ReservationJdbcTemplateRepository implements ReservationRepository{

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Reservation> viewReservationsForHost(Host host){
        final String sql = """
                SELECT r.reservation_id, r.start_date, r.end_date, r.total, u.user_id, u.first_name, u.last_name, u.email FROM reservation r
                JOIN user u on r.guest_user_id = u.user_id
                JOIN location on r.location_id = location.location_id
                WHERE location.user_id = ?
                ORDER BY r.start_date ASC
                """;
        return jdbcTemplate.query(sql, new ReservationMapper(), host.getId());
    }

    @Override
    public List<Reservation> viewReservationsForHostAndGuest(Host host, Guest guest){
        final String sql = """
                SELECT r.reservation_id, r.start_date, r.end_date, r.total, u.user_id, u.first_name, u.last_name, u.email FROM reservation r
                JOIN user u on r.guest_user_id = u.user_id
                JOIN location on r.location_id = location.location_id
                WHERE location.user_id = ? AND r.guest_user_id = ?
                ORDER BY r.start_date ASC
                """;
        return jdbcTemplate.query(sql, new ReservationMapper(), host.getId(), guest.getId());
    }

    @Override
    public Reservation addReservation(Reservation reservation) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("reservation_id");

        HashMap<String, Object> args = new HashMap<>();
        args.put("location_id", reservation.getHost().getLocation().getId());
        args.put("guest_user_id", reservation.getGuest().getId());
        args.put("start_date", reservation.getStartDate());
        args.put("end_date", reservation.getEndDate());
        args.put("total", reservation.getTotal());


        int reservationId = insert.executeAndReturnKey(args).intValue();
        reservation.setId(reservationId);

        return reservation;
    }

    @Override
    public int updateReservation(Reservation reservation){
        String updateString = """
                    UPDATE reservation 
                    SET total = ?, start_date = ?, end_date = ?
                    WHERE reservation_id = ?
                """;
       return jdbcTemplate.update(updateString, reservation.getTotal(), reservation.getStartDate(), reservation.getEndDate(), reservation.getId());
    }

    @Override
    public boolean deleteReservation(Reservation reservation){
        String deleteString = """
                    DELETE FROM reservation 
                    WHERE reservation_id = ?
                """;
        return jdbcTemplate.update(deleteString, reservation.getId()) > 0;
    }
}
