package learn;

import learn.data.HostJdbcTemplateRepository;
import learn.data.ReservationJdbcTemplateRepository;
import learn.domain.HostService;
import learn.domain.ReservationService;
import learn.ui.ConsoleIO;
import learn.ui.Controller;
import learn.ui.View;
import org.springframework.jdbc.core.JdbcTemplate;


public class App {
    public static void main(String[] args) {
        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
        HostJdbcTemplateRepository hostRepository = new HostJdbcTemplateRepository(jdbcTemplate);
        ReservationJdbcTemplateRepository reservationRepository = new ReservationJdbcTemplateRepository(jdbcTemplate);
        HostService hostService = new HostService(hostRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);
        Controller controller = new Controller(view, hostService, reservationService);
        controller.run();
    }
}