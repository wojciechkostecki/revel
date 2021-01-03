package pl.wojciechkostecki.revel.controller;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.model.MenuItem;
import pl.wojciechkostecki.revel.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/menuitem")
public class MenuItemController {
    private final Logger logger = LoggerFactory.getLogger(MenuItemController.class);
    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        logger.debug("REST request to create Menu Item: {}", menuItem);
        MenuItem savedMenuItem = menuItemService.save(menuItem);
        return new ResponseEntity<>(savedMenuItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems(){
        logger.debug("REST request to get all Menu Items");
        return ResponseEntity.ok(menuItemService.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> getMenuItemsByName (@RequestParam String name){
        logger.debug("REST request to get Menu Items with name containing {}", name);
        return ResponseEntity.ok(menuItemService.findByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuItem){
        logger.debug("REST request to update Menu Item: {} with id {}", menuItem, id);
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, menuItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        logger.debug("REST request to delete Menu Item", id);
        menuItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
