package co.id.jss.jssrestapi.service;

import co.id.jss.jssrestapi.domain.Client;
import co.id.jss.jssrestapi.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VersionService {

    private final ClientRepository clientRepository;

    @Autowired
    public VersionService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Cacheable(value = "starterCache")
    public Optional<Client> getVersionByIdAndIsActive(String id, boolean isActive){
        return clientRepository.findOneByIdAndIsActive(id, isActive);
    }
}
