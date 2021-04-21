package pl.wojciechkostecki.revel.service;

import org.springframework.stereotype.Service;
import pl.wojciechkostecki.revel.mapper.UserMapper;
import pl.wojciechkostecki.revel.model.User;
import pl.wojciechkostecki.revel.model.dto.UserDTO;
import pl.wojciechkostecki.revel.repository.UserRepository;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public RegisterService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByLogin(userDTO.getLogin()).isPresent()) {
            throw new RuntimeException("There is a user with given login");
        }
        return userRepository.save(userMapper.toEntity(userDTO));
    }
}
