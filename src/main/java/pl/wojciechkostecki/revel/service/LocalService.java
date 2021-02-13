package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.mapper.LocalMapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.dto.LocalDTO;
import pl.wojciechkostecki.revel.repository.LocalRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocalService {
    private final LocalRepository localRepository;
    private final LocalMapper localMapper;

    public LocalService(LocalRepository localRepository, LocalMapper localMapper) {
        this.localRepository = localRepository;
        this.localMapper = localMapper;
    }


    public Local save(LocalDTO localDTO) {
        Local local = localMapper.toEntity(localDTO);
        return localRepository.save(local);
    }

    public List<Local> getAll() {
        return localRepository.findAll();
    }

    public Optional<Local> findById(Long id) {
        return localRepository.findById(id);
    }

    public List<Local> findByName(String name) {
        return localRepository.findByNameContainingIgnoreCase(name);
    }

    public Local updateLocal(Long id, LocalDTO localDTO) {
        Local modifiedLocal = localRepository.getOne(id);
        LocalDTO modifiedLocalDTO = localMapper.toDto(modifiedLocal);
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