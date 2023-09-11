package learn.dwmh.data;

import learn.dwmh.models.Guest;
import learn.dwmh.models.Host;
import org.springframework.jdbc.core.JdbcTemplate;

public class GuestJdbcTemplateRepository implements GuestRepository {

    private final JdbcTemplate jdbcTemplate;

    public GuestJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Guest findByEmail(String email){
        final String sql = """
                SELECT user_id, first_name, last_name, email FROM user
                WHERE email like ?
                """;
        return jdbcTemplate.query(sql, new GuestMapper(), email)
                .stream().findFirst().orElse(null);
    }
}
