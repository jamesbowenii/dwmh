package learn.data;

import learn.models.Host;
import learn.models.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HostMapper implements RowMapper<Host> {

    @Override
    public Host mapRow(ResultSet rs, int rowNum) throws SQLException{
        Location location = new Location();
            location.setId(rs.getInt("location_id"));
            location.setAddress(rs.getString("address"));
            location.setCity(rs.getString("city"));
            location.setPostalCode(rs.getString("postal_code"));
            location.setState(rs.getString("name"));
            location.setStandardRate(rs.getBigDecimal("standard_rate"));
            location.setWeekendRate(rs.getBigDecimal("weekend_rate"));

       Host host = new Host();
       host.setId(rs.getInt("user_id"));
       host.setEmail(rs.getString("email"));
       host.setFirstName(rs.getString("first_name"));
       host.setLastName(rs.getString("last_name"));
       host.setLocation(location);
       location.setHost(host);
       return host;
    }
}
