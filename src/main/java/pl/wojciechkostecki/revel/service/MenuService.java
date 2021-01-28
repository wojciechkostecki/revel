package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.mapper.MenuMapper;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;

    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    public Menu save(MenuDTO menuDTO){
        Menu menu = menuMapper.toEntity(menuDTO);
        return menuRepository.save(menu);
    }

    public List<Menu> getAll(){
        return menuRepository.findAll();
    }

    public Menu updateMenu(Long id, MenuDTO menuDTO){
        Menu modifiedMenu = menuRepository.getOne(id);
        MenuDTO modifiedMenuDTO = menuMapper.toDto(modifiedMenu);
        modifiedMenuDTO.setName(menuDTO.getName());
        modifiedMenuDTO.setMenuItems(menuDTO.getMenuItems());
        return save(modifiedMenuDTO);
    }

    public void delete(Long id){
        menuRepository.deleteById(id);
    }
}
