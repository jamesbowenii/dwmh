package learn.dwmh.data;

import learn.dwmh.models.Guest;
import learn.dwmh.models.Host;

public interface GuestRepository {

    public Guest findByEmail(String email);
}
