package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.exception.MenuIsAlreadyAssignedException;
import pl.wojciechkostecki.revel.mapper.MenuMapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

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
        Local local = localService.findById(menuDTO.getLocalId());
        if (Objects.nonNull(local.getMenu())) {
            throw new MenuIsAlreadyAssignedException(local.getMenu().getId(), menuDTO.getLocalId());
        }
        menu.setLocal(local);
        local.setMenu(menu);
        return menuRepository.save(menu);
    }

    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Couldn't find a menu with id: %d", id)));
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
