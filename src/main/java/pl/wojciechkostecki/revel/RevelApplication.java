package pl.wojciechkostecki.revel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wojciechkostecki.revel.model.Role;
import pl.wojciechkostecki.revel.model.User;
import pl.wojciechkostecki.revel.model.UserRole;
import pl.wojciechkostecki.revel.repository.UserRepository;
import pl.wojciechkostecki.revel.repository.UserRoleRepository;

@SpringBootApplication
public class RevelApplication {
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RevelApplication(UserRoleRepository userRoleRepository, UserRepository userRepository,
                            PasswordEncoder passwordEncoder) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
            user.setPassword(passwordEncoder.encode("admin"));
            user.getRoles().add(userRoleRepository.findByName(Role.ADMIN));
            user.getRoles().add(userRoleRepository.findByName(Role.USER));
            userRepository.save(user);
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
