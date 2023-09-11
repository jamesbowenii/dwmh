package learn.dwmh.data;

import learn.dwmh.models.Host;
import org.springframework.jdbc.core.JdbcTemplate;

public class HostJdbcTemplateRepository implements HostRepository {

    private final JdbcTemplate jdbcTemplate;

    public HostJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Host findByEmail(String email){
        final String sql = """
                SELECT user.user_id, user.first_name, user.last_name, user.email, location.location_id, location.address, location.city, location.postal_code, location.weekend_rate, location.standard_rate, state.name FROM user
                JOIN location on user.user_id = location.user_id
                JOIN state on location.state_id = state.state_id
                WHERE email like ?
                """;
                return jdbcTemplate.query(sql, new HostMapper(), email)
                        .stream().findFirst().orElse(null);
    }
}
