package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.mapper.MenuItemMapper;
import pl.wojciechkostecki.revel.mapper.MenuMapper;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.model.dto.MenuItemDTO;
import pl.wojciechkostecki.revel.repository.MenuItemRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuService menuService;
    private final MenuItemMapper itemMapper;
    private final MenuMapper menuMapper;

    public MenuItemService(MenuItemRepository menuItemRepository, MenuService menuService, MenuItemMapper itemMapper, MenuMapper menuMapper) {
        this.menuItemRepository = menuItemRepository;
        this.menuService = menuService;
        this.itemMapper = itemMapper;
        this.menuMapper = menuMapper;
    }

    public MenuItemDTO save(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = itemMapper.toEntity(menuItemDTO);
        Menu menu = menuMapper.toEntity(menuService.findById(menuItemDTO.getMenuId())
                .orElseThrow(() -> new EntityNotFoundException
                        (String.format("Couldn't find a menu with passed id: %d", menuItemDTO.getMenuId()))));
        menuItem.setMenu(menu);
        menu.getMenuItems().add(menuItem);
        return itemMapper.toDto(menuItemRepository.save(menuItem));
    }

    public List<MenuItemDTO> getAll() {
        return itemMapper.toDto(menuItemRepository.findAll());
    }

    public List<MenuItemDTO> findByName(String name) {
        return itemMapper.toDto(menuItemRepository.findByNameContainingIgnoreCase(name));
    }

    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem menuItem = itemMapper.toEntity(menuItemDTO);
        MenuItem modifiedMenuItem = menuItemRepository.getOne(id);
        modifiedMenuItem.setCategory(menuItem.getCategory());
        modifiedMenuItem.setName(menuItem.getName());
        modifiedMenuItem.setDescription(menuItem.getDescription());
        modifiedMenuItem.setPrice(menuItem.getPrice());
        return itemMapper.toDto(menuItemRepository.save(modifiedMenuItem));
    }

    public void delete(Long id) {
        menuItemRepository.deleteById(id);
    }
}
