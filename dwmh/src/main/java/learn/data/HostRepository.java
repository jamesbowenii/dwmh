package learn.data;

import learn.models.Host;

public interface HostRepository {

    public Host findByEmail(String email);
}
