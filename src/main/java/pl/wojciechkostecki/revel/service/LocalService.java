package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.repository.LocalRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LocalService {
    private final LocalRepository localRepository;

    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    public List<Local> getAll() {
        return localRepository.findAll();
    }

    public Local findById(Long id) {
        return localRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a local with id: %s", id)));
    }
}
