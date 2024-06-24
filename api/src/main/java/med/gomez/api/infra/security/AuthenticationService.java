package med.gomez.api.infra.security;

import med.gomez.api.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marca esta clase como un servicio de Spring, lo que permite que sea detectada automáticamente por el escaneo de componentes de Spring
public class AuthenticationService implements UserDetailsService {

    @Autowired // Inyecta automáticamente una instancia de UserRepository
    private UserRepository userRepository;

    @Override // Sobrescribe el método de la interfaz UserDetailsService
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Carga el usuario desde el repositorio utilizando el nombre de usuario proporcionado
        // Si el usuario no se encuentra, se lanzará una excepción UsernameNotFoundException
        return userRepository.findByLogin(username);
    }
}
