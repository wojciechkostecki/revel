package pl.wojciechkostecki.revel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.model.dto.MenuDTO;
import pl.wojciechkostecki.revel.service.MenuService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuController {
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@Valid @RequestBody MenuDTO menuDTO) {
        logger.debug("REST request to create Menu: {}", menuDTO);
        MenuDTO savedMenu = menuService.save(menuDTO);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        logger.debug("REST request to get all Menu");
        return ResponseEntity.ok(menuService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long id, @Valid @RequestBody MenuDTO menuDTO) {
        logger.debug("REST request to update Menu: {} with id {}", menuDTO, id);
        return ResponseEntity.ok(menuService.updateMenu(id, menuDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        logger.debug("REST request to delete Menu: {}", id);
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
