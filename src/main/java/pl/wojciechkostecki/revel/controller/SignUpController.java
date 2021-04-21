package pl.wojciechkostecki.revel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojciechkostecki.revel.model.dto.UserDTO;
import pl.wojciechkostecki.revel.service.RegisterService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
public class SignUpController {
    private final RegisterService registerService;

    public SignUpController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<Void> registrationUser(@Valid @RequestBody UserDTO userDTO){
        registerService.registerUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
