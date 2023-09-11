package learn.dwmh.data;

import learn.dwmh.models.Host;

public interface HostRepository {

    public Host findByEmail(String email);
}
