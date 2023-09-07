package learn.data;

import learn.models.Host;
import learn.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;

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

}
