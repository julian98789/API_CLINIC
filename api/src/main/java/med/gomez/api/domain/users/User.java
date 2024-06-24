package med.gomez.api.domain.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios") // Define la tabla en la base de datos que corresponde a esta entidad
@Entity(name = "usuario") // Define el nombre de la entidad en JPA
@Getter // Genera automáticamente getters para todos los campos utilizando Lombok
@NoArgsConstructor // Genera automáticamente un constructor sin argumentos utilizando Lombok
@AllArgsConstructor // Genera automáticamente un constructor con todos los argumentos utilizando Lombok
@EqualsAndHashCode(of = "id") // Genera automáticamente métodos equals() y hashCode() basados en el campo "id" utilizando Lombok
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el campo "id" es una clave primaria generada automáticamente
    private Long id;

    private String login;
    private String clue; // Renombrado desde "password" para evitar confusión con el método getPassword()

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Devuelve una colección de autoridades otorgadas al usuario
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Devuelve la contraseña del usuario
        return clue;
    }

    @Override
    public String getUsername() {
        // Devuelve el nombre de usuario
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indica si la cuenta del usuario no ha expirado
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indica si la cuenta del usuario no está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indica si las credenciales del usuario no han expirado
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Indica si la cuenta del usuario está habilitada
        return true;
    }
}
