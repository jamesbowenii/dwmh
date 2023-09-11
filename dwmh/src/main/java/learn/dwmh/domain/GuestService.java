package learn.dwmh.domain;

import learn.dwmh.data.GuestRepository;
import learn.dwmh.models.Guest;

public class GuestService {
    private final GuestRepository repository;

    public GuestService(GuestRepository repository) {
        this.repository = repository;
    }

    public Guest findByEmail(String email){
        return repository.findByEmail(email);
    }
}
