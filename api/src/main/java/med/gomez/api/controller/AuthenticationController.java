package med.gomez.api.controller;

import jakarta.validation.Valid;
import med.gomez.api.domain.users.userAuthenticationData;
import med.gomez.api.infra.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid userAuthenticationData userAuthenticationData){
        Authentication token = new UsernamePasswordAuthenticationToken(userAuthenticationData.login()
                ,userAuthenticationData.clue());
        authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
