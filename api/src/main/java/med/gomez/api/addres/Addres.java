package med.gomez.api.addres;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Addres {
    String street;
    String district;
    String city;
    String number;
    String complement;

    public Addres(AddressData addres) {
        this.street = addres.street();
        this.district = addres.district();
        this.city = addres.city();
        this.number = addres.number();
        this.complement = addres.complement();
    }
}
