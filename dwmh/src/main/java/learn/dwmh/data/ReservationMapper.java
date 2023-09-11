package learn.dwmh.data;

import learn.dwmh.models.Guest;
import learn.dwmh.models.Reservation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper {

    @Override
    public Reservation mapRow(ResultSet rs, int row)throws SQLException {
        Guest guest = new Guest();
        guest.setEmail(rs.getString("email"));
        guest.setId(rs.getInt("user_id"));
        guest.setFirstName(rs.getString("first_name"));
        guest.setLastName(rs.getString("last_name"));

        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("reservation_id"));
        reservation.setGuest(guest);
        reservation.setEndDate(rs.getDate("end_date").toLocalDate());
        reservation.setStartDate(rs.getDate("start_date").toLocalDate());
        reservation.setTotal(rs.getBigDecimal("total"));

        return reservation;
    }
}
