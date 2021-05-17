package pl.wojciechkostecki.revel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.wojciechkostecki.revel.mapper.UserMapper;
import pl.wojciechkostecki.revel.model.Role;
import pl.wojciechkostecki.revel.model.User;
import pl.wojciechkostecki.revel.model.UserRole;
import pl.wojciechkostecki.revel.repository.UserRepository;
import pl.wojciechkostecki.revel.repository.UserRoleRepository;
import pl.wojciechkostecki.revel.service.RegisterService;

@SpringBootApplication
public class RevelApplication {
    private final RegisterService registerService;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public RevelApplication(RegisterService registerService, UserMapper userMapper, UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.registerService = registerService;
        this.userMapper = userMapper;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RevelApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterInitSetup() {
        saveRoles();
        saveUsers();
    }

    private void saveUsers() {
        if(!userRepository.findByUsername("admin").isPresent()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("admin");
            user.getRoles().add(userRoleRepository.findByName(Role.ADMIN));
            registerService.registerUser(userMapper.toDto(user));
        }
    }

    private void saveRoles() {
        for (Role role : Role.values()) {
            if(!userRoleRepository.existsByName(role)) {
                UserRole userRole = new UserRole();
                userRole.setName(role);
                userRoleRepository.save(userRole);
            }
        }
    }
}
