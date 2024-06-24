package med.gomez.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.gomez.api.domain.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service // Indica que esta clase es un servicio de Spring
public class TokenService {

    @Value("&{api.security.secret}") // Inyecta el valor de la propiedad 'api.security.secret' desde el archivo de configuración
    private String ApiSecret;

    // Método para generar un token JWT para un usuario dado
    public String generateToken(User user) {
        try {
            // Crea el algoritmo HMAC256 con el secreto proporcionado
            Algorithm algorithm = Algorithm.HMAC256(ApiSecret);
            // Construye y firma el token JWT
            return JWT.create()
                    .withIssuer("GomezClinic") // Establece el emisor del token
                    .withSubject(user.getLogin()) // Establece el sujeto del token como el login del usuario
                    .withClaim("id", user.getId()) // Agrega un reclamo personalizado con el ID del usuario
                    .withExpiresAt(generateExpirationDate()) // Establece la fecha de expiración del token
                    .sign(algorithm); // Firma el token con el algoritmo
        } catch (JWTCreationException exception) {
            // Maneja la excepción si ocurre un error durante la creación del token
            throw new RuntimeException();
        }
    }

    // Método privado para generar la fecha de expiración del token (3 horas desde ahora)
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-05:00"));
    }
}