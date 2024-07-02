package med.gomez.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.gomez.api.domain.users.User;
import med.gomez.api.domain.users.userAuthenticationData;
import med.gomez.api.infra.security.JWTTokenData;
import med.gomez.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/login") // Define la ruta base para este controlador
@Tag(name = "Autenticacion", description = "Obtiene el token para el usuario correspondiente que permite el acceso a los demás endpoints.")
public class AuthenticationController {

    @Autowired // Inyecta automáticamente una instancia de AuthenticationManager
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping // Define que este método maneja las solicitudes POST a /login
    @Operation(summary = "Autenticar usuario", description = "Autentica al usuario con las credenciales proporcionadas y genera un token JWT.")
    public ResponseEntity authenticateUser(@RequestBody @Valid userAuthenticationData userAuthenticationData) {
        // Crea un token de autenticación con las credenciales del usuario
        Authentication authToken = new UsernamePasswordAuthenticationToken(userAuthenticationData.login(), userAuthenticationData.clue());

        // Autentica el token utilizando el AuthenticationManager
        var authenticatedUser = authenticationManager.authenticate(authToken);

        // Genera un token JWT para el usuario autenticado
        var JWTtoken= tokenService.generateToken((User) authenticatedUser.getPrincipal());

        // Devuelve una respuesta HTTP 200 OK si la autenticación es exitosa
        return ResponseEntity.ok(new JWTTokenData(JWTtoken));
    }
}
