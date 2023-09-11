package learn.dwmh;

import learn.dwmh.data.GuestJdbcTemplateRepository;
import learn.dwmh.data.HostJdbcTemplateRepository;
import learn.dwmh.data.ReservationJdbcTemplateRepository;
import learn.dwmh.domain.GuestService;
import learn.dwmh.domain.HostService;
import learn.dwmh.domain.ReservationService;
import learn.dwmh.ui.ConsoleIO;
import learn.dwmh.ui.Controller;
import learn.dwmh.ui.View;
import org.springframework.jdbc.core.JdbcTemplate;


public class App {
    public static void main(String[] args) {
        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        JdbcTemplate jdbcTemplate = DataHelper.getJdbcTemplate();
        HostJdbcTemplateRepository hostRepository = new HostJdbcTemplateRepository(jdbcTemplate);
        ReservationJdbcTemplateRepository reservationRepository = new ReservationJdbcTemplateRepository(jdbcTemplate);
        GuestJdbcTemplateRepository guestRepository = new GuestJdbcTemplateRepository(jdbcTemplate);
        HostService hostService = new HostService(hostRepository);
        GuestService guestService = new GuestService(guestRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);
        Controller controller = new Controller(view, hostService, reservationService, guestService);
        controller.run();
    }
}