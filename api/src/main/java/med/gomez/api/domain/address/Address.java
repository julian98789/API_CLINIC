package med.gomez.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable // Indica que esta clase puede ser embebida en otra entidad JPA
@Getter // Genera automáticamente getters para todos los campos utilizando Lombok
@NoArgsConstructor // Genera automáticamente un constructor sin argumentos utilizando Lombok
@AllArgsConstructor // Genera automáticamente un constructor con todos los argumentos utilizando Lombok
public class Address {
    String street;
    String district;
    String city;
    String number;
    String complement;

    // Constructor que inicializa los campos de la clase a partir de un objeto AddressData
    public Address(AddressData addres) {
        this.street = addres.street();
        this.district = addres.district();
        this.city = addres.city();
        this.number = addres.number();
        this.complement = addres.complement();
    }

    // Método para actualizar los datos de la dirección a partir de un objeto AddressData
    public Address updateData(AddressData addres) {
        this.street = addres.street();
        this.district = addres.district();
        this.city = addres.city();
        this.number = addres.number();
        this.complement = addres.complement();
        return this; // Devuelve la instancia actualizada
    }
}
