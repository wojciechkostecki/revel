package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.repository.MenuItemRepository;

import java.util.List;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public MenuItem save(MenuItem menuItem){
        return menuItemRepository.save(menuItem);
    }

    public List<MenuItem> getAll() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> findByName(String name){
        return menuItemRepository.findByNameContainingIgnoreCase(name);
    }

    public MenuItem updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem modifiedMenuItem = menuItemRepository.getOne(id);
        modifiedMenuItem.setCategory(menuItem.getCategory());
        modifiedMenuItem.setName(menuItem.getName());
        modifiedMenuItem.setIngredients(menuItem.getIngredients());
        modifiedMenuItem.setPrice(menuItem.getPrice());
        return save(modifiedMenuItem);
    }

    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }
}
