package med.gomez.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.gomez.api.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Indica que esta clase es un componente gestionado por Spring
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired // Inyecta automáticamente una instancia de TokenService
    private TokenService tokenService;

    @Autowired // Inyecta automáticamente una instancia de UserRepository
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtiene el encabezado Authorization de la solicitud HTTP
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) { // Verifica si el encabezado Authorization no es nulo
            // Elimina el prefijo "Bearer " del token
            var token = authHeader.replace("Bearer ", "");
            // Extrae el nombre de usuario del token
            var userName = tokenService.getSubject(token);
            if (userName != null) { // Verifica si el nombre de usuario no es nulo
                // El token es válido
                var user = userRepository.findByLogin(userName); // Busca el usuario en la base de datos
                // Crea un objeto de autenticación con el usuario y sus autoridades
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
