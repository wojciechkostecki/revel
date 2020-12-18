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

    public Local save(Local local) {
        return localRepository.save(local);
    }

    public List<Local> getAll() {
        return localRepository.findAll();
    }

    public Local findById(Long id) {
        return localRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a local with id: %s", id)));
    }

    public List<Local> findByName(String name) {
        return localRepository.findByName(name);
    }

    public Local updateLocal(Long id, Local local) {
        if(!id.equals(local.getId())){
            throw new IllegalArgumentException(String.format("Path id %s not matching body id %s", id, local.getId()));
        }
        return save(local);
    }

    public void delete(Long id) {
        localRepository.deleteById(id);
    }
}
