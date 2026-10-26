package ma.ensaf.TP2.service;



import ma.ensaf.TP2.model.Role;
import ma.ensaf.TP2.model.User;
import ma.ensaf.TP2.repository.RoleRepository;
import ma.ensaf.TP2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitData {

    @Bean
    CommandLineRunner initDatabase(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            String[] roles = {
                    "ADMIN",
                    "PHARMACIEN",
                    "CAISSIER",
                    "CLIENT"
            };

            for (String roleName : roles) {
                if (roleRepository.findByName(roleName).isEmpty()) {
                    roleRepository.save(new Role(roleName));
                }
            }

            createUser(
                    userRepository,
                    roleRepository,
                    passwordEncoder,
                    "admin",
                    "admin123",
                    "ADMIN"
            );

            createUser(
                    userRepository,
                    roleRepository,
                    passwordEncoder,
                    "pharma",
                    "pharma123",
                    "PHARMACIEN"
            );

            createUser(
                    userRepository,
                    roleRepository,
                    passwordEncoder,
                    "caisse",
                    "caisse123",
                    "CAISSIER"
            );

            createUser(
                    userRepository,
                    roleRepository,
                    passwordEncoder,
                    "client",
                    "client123",
                    "CLIENT"
            );
        };
    }

    private void createUser(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            String username,
            String password,
            String roleName) {

        if (userRepository.findByUsername(username).isPresent()) {
            return;
        }

        Role role = roleRepository.findByName(roleName)
                .orElseThrow();

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);
    }
}