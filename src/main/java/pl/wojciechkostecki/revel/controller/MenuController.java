package pl.wojciechkostecki.revel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.model.Menu;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
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
    public ResponseEntity<Menu> createMenu(@RequestBody MenuDTO menuDTO) {
        logger.debug("REST request to create Menu: {}", menuDTO);
        Menu savedMenu = menuService.save(menuDTO);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        logger.debug("REST request to get all Menu");
        return ResponseEntity.ok(menuService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        logger.debug("REST request to update Menu: {} with id {}", menuDTO, id);
        return ResponseEntity.ok(menuService.updateMenu(id, menuDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        logger.debug("REST request to delete Menu: {}", id);
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
