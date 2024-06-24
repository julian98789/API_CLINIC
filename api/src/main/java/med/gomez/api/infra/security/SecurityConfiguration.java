package med.gomez.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta clase es una fuente de definiciones de beans para el contexto de la aplicación
@EnableWebSecurity // Habilita la seguridad web para esta aplicación
public class SecurityConfiguration {

    // Define un bean para la configuración del filtro de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Configura la política de creación de sesiones como STATELESS

        return httpSecurity.build(); // Construye y devuelve el SecurityFilterChain
    }

    // Define un bean para el AuthenticationManager, que se encarga de la autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Obtiene y devuelve el AuthenticationManager desde la configuración de autenticación
    }

    // Define un bean para BCryptPasswordEncoder, que se utiliza para cifrar contraseñas
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(); // Crea y devuelve una instancia de BCryptPasswordEncoder
    }
}
