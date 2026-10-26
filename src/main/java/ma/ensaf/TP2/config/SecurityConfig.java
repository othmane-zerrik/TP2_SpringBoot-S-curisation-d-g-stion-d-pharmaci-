package ma.ensaf.TP2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Pages accessibles sans connexion
                        .requestMatchers("/login", "/css/**", "/js/**")
                        .permitAll()

                        // Administration
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Gestion des médicaments
                        .requestMatchers(HttpMethod.POST, "/medicaments/**")
                        .hasAnyRole("ADMIN", "PHARMACIEN")

                        .requestMatchers(HttpMethod.PUT, "/medicaments/**")
                        .hasAnyRole("ADMIN", "PHARMACIEN")

                        .requestMatchers(HttpMethod.DELETE, "/medicaments/**")
                        .hasRole("ADMIN")

                        // Consultation
                        .requestMatchers(HttpMethod.GET, "/medicaments/**")
                        .hasAnyRole(
                                "ADMIN",
                                "PHARMACIEN",
                                "CAISSIER",
                                "CLIENT"
                        )

                        // Enregistrement des ventes
                        .requestMatchers("/ventes/**")
                        .hasAnyRole("ADMIN", "PHARMACIEN", "CAISSIER")

                        // Toute autre route nécessite une connexion
                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}