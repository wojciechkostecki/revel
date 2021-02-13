package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.exception.BadRequestException;
import pl.wojciechkostecki.revel.mapper.MenuMapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final LocalService localService;
    private final MenuMapper menuMapper;

    public MenuService(MenuRepository menuRepository, LocalService localService, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.localService = localService;
        this.menuMapper = menuMapper;
    }

    public Menu save(MenuDTO menuDTO) {
        Menu menu = menuMapper.toEntity(menuDTO);
        Local local = localService.findById(menuDTO.getLocalId())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a local with passed id"));
        if (Objects.nonNull(local.getMenu())) {
            throw new BadRequestException("Menu is already assigned to local");
        }
        menu.setLocal(local);
        local.setMenu(menu);
        return menuRepository.save(menu);
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    public Optional<Menu> findById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu updateMenu(Long id, MenuDTO menuDTO) {
        Menu menu = menuMapper.toEntity(menuDTO);
        Menu modifiedMenu = menuRepository.getOne(id);
        modifiedMenu.setName(menu.getName());
        modifiedMenu.setMenuItems(menu.getMenuItems());
        return menuRepository.save(modifiedMenu);
    }

    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}
