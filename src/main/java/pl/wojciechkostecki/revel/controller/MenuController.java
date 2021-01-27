package pl.wojciechkostecki.revel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        logger.debug("REST request to create Menu: {}", menu);
        Menu savedMenu = menuService.save(menu);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        logger.debug("REST request to get all Menu");
        return ResponseEntity.ok(menuService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        logger.debug("REST request to update Menu: {} with id {}", menu, id);
        return ResponseEntity.ok(menuService.updateMenu(id, menu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        logger.debug("REST request to delete Menu", id);
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
