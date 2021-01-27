package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.mapper.LocalMapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.dto.LocalDTO;
import pl.wojciechkostecki.revel.repository.LocalRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class LocalService {
    private final LocalRepository localRepository;
    private LocalMapper localMapper;

    public LocalService(LocalRepository localRepository, LocalMapper localMapper) {
        this.localRepository = localRepository;
        this.localMapper = localMapper;
    }


    public Local save(LocalDTO localDTO) {
        Local local = localMapper.toLocal(localDTO);
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

    public Local updateLocal(Long id, LocalDTO localDTO) {
        Local modifiedLocal = localRepository.getOne(id);
        LocalDTO modifiedLocalDTO = localMapper.toLocalDTO(modifiedLocal);
        modifiedLocalDTO.setName(localDTO.getName());
        modifiedLocalDTO.setOpeningTime(localDTO.getOpeningTime());
        modifiedLocalDTO.setClosingTime(localDTO.getClosingTime());
        modifiedLocalDTO.setMenu(localDTO.getMenu());
        return save(modifiedLocalDTO);
    }

    public void delete(Long id) {
        localRepository.deleteById(id);
    }
}