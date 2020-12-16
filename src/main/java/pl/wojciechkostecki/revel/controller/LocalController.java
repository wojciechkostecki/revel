package pl.wojciechkostecki.revel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechkostecki.revel.model.Local;
import pl.wojciechkostecki.revel.service.LocalService;

import java.util.List;

@RestController
@RequestMapping("/api/locals")
public class LocalController {
    private final Logger logger = LoggerFactory.getLogger(LocalController.class);
    private final LocalService localService;

    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @PostMapping
    public ResponseEntity<Local> createLocal(@RequestBody Local local) {
        logger.debug("REST request to create Local: {}",local);
        Local savedLocal = localService.save(local);
        return new ResponseEntity<>(savedLocal, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Local>> getAllLocals(){
        logger.debug("REST request to get all Locals");
        return ResponseEntity.ok(localService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Local> getLocals(@PathVariable Long id){
        logger.debug("REST request to get Local: {}",id);
        return ResponseEntity.ok(localService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Local> updateLocal(@PathVariable Long id, @RequestBody Local local){
        logger.debug("REST request to update Local: {} with id {}",local,id);
        return ResponseEntity.ok(localService.updateLocal(id,local));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id){
        logger.debug("REST request to delete Local: {}",id);
        localService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
