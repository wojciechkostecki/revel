package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Menu save(Menu menu){
        return menuRepository.save(menu);
    }

    public List<Menu> getAll(){
        return menuRepository.findAll();
    }

    public List<Menu> findByName(String name){
        return menuRepository.findByNameContainingIgnoreCase(name);
    }

    public Menu updateMenu(Long id, Menu menu){
        Menu modifiedMenu = menuRepository.getOne(id);
        modifiedMenu.setName(menu.getName());
        modifiedMenu.setMenuItems(menu.getMenuItems());
        return save(modifiedMenu);
    }

    public void delete(Long id){
        menuRepository.deleteById(id);
    }
}
