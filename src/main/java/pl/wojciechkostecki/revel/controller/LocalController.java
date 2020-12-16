package pl.wojciechkostecki.revel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
