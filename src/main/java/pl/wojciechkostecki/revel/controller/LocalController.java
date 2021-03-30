package pl.wojciechkostecki.revel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.mapper.LocalMapper;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.model.dto.LocalDTO;
import pl.wojciechkostecki.revel.service.LocalService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/locals")
public class LocalController {
    private final Logger logger = LoggerFactory.getLogger(LocalController.class);
    private final LocalService localService;
    private final LocalMapper localMapper;

    public LocalController(LocalService localService, LocalMapper localMapper) {
        this.localService = localService;
        this.localMapper = localMapper;
    }

    @PostMapping
    public ResponseEntity<Local> createLocal(@Valid @RequestBody LocalDTO localDTO) {
        logger.debug("REST request to create Local: {}", localDTO);
        Local savedLocal = localService.save(localDTO);
        return new ResponseEntity<>(savedLocal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Local>> getAllLocals() {
        logger.debug("REST request to get all Locals");
        return ResponseEntity.ok(localService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocal(@PathVariable Long id) {
        logger.debug("REST request to get Local: {}", id);
        return ResponseEntity.ok(localService.findById(id).get());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Local>> getLocalsByName(@RequestParam String name) {
        logger.debug("REST request to get Locals with name containing {}", name);
        return ResponseEntity.ok(localService.findByName(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalDTO> updateLocal(@PathVariable Long id, @Valid @RequestBody LocalDTO localDTO) {
        logger.debug("REST request to update Local: {} with id {}", localDTO, id);
        return ResponseEntity.ok(localMapper.toDto(localService.updateLocal(id, localDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        logger.debug("REST request to delete Local: {}", id);
        localService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
