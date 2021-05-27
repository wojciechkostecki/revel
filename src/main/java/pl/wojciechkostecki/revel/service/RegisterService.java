package pl.wojciechkostecki.revel.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.exception.UserAlreadyExistException;
import pl.wojciechkostecki.revel.mapper.UserMapper;
import pl.wojciechkostecki.revel.model.dto.UserDTO;
import pl.wojciechkostecki.revel.repository.UserRepository;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("There is a user with given login");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userMapper.toEntity(userDTO));
    }
}
