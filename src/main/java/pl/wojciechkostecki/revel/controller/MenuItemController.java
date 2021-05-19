package pl.wojciechkostecki.revel.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.model.dto.MenuItemDTO;
import pl.wojciechkostecki.revel.service.MenuItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {
    private final Logger logger = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO menuItemDTO) {
        logger.debug("REST request to create Menu Item: {}", menuItemDTO);
        MenuItemDTO savedMenuItem = menuItemService.save(menuItemDTO);
        return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        logger.debug("REST request to get all Menu Items");
        return ResponseEntity.ok(menuItemService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByName(@RequestParam String name) {
        logger.debug("REST request to get Menu Items with name containing {}", name);
        return ResponseEntity.ok(menuItemService.findByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemDTO menuItemDTO) {
        logger.debug("REST request to update Menu Item: {} with id {}", menuItemDTO, id);
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        logger.debug("REST request to delete Menu Item: {}", id);
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
