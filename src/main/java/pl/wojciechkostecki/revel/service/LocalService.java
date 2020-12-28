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
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a local with id: %d", id)));
    }

    public List<Local> findByName(String name) {
        return localRepository.findByNameContainingIgnoreCase(name);
    }

    public Local updateLocal(Long id, Local local) {
        Local modifiedLocal = localRepository.getOne(id);
        modifiedLocal.setName(local.getName());
        modifiedLocal.setOpeningTime(local.getOpeningTime());
        modifiedLocal.setClosingTime(local.getClosingTime());
        modifiedLocal.setMenu(local.getMenu());
        return save(modifiedLocal);
    }

    public void delete(Long id) {
        localRepository.deleteById(id);
    }
}