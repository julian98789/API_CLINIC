package med.gomez.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
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
            Algorithm algorithm = Algorithm.HMAC256(ApiSecret); // el tipo de algoritmo de encriptacion
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

    // Método para obtener el sujeto del token JWT (el nombre de usuario) si es válido
    public String getSubject(String token) {
        if (token == null){
            // Lanza una excepción si el token es nulo
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            // Crea el algoritmo HMAC256 con el secreto proporcionado para verificar el token
            Algorithm algorithm = Algorithm.HMAC256(ApiSecret);
            // Verifica y decodifica el token
            verifier = JWT.require(algorithm)
                    .withIssuer("GomezClinic") // Verifica el emisor del token
                    .build()
                    .verify(token); // Verifica el token
            verifier.getSubject(); // Obtiene el sujeto del token (login del usuario)

        } catch (JWTVerificationException exception) {
            // Maneja la excepción si ocurre un error durante la verificación del token
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null){
            // Lanza una excepción si el sujeto del token es nulo
            throw new RuntimeException("verifier invalido");
        }
        return verifier.getSubject(); // Retorna el sujeto del token
    }

    // Método privado para generar la fecha de expiración del token (3 horas desde ahora)
    private Instant generateExpirationDate (){
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-05:00"));
    }
}
