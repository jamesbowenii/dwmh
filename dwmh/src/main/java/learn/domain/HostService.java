package learn.domain;

import learn.data.HostRepository;
import learn.models.Host;

public class HostService {
    private final HostRepository hostRepository;

    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public Host findByEmail(String email){

        return hostRepository.findByEmail(email);
    }

    private Result<Host> validate(Host host){
        Result<Host> result = new Result<>();
        if(host == null){
            result.addErrorMessage("Host is null");
            return result;
        }

            return result;
    }
}
