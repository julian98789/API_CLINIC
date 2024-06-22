package med.gomez.api.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    String street;
    String district;
    String city;
    String number;
    String complement;

    public Address(AddressData addres) {
        this.street = addres.street();
        this.district = addres.district();
        this.city = addres.city();
        this.number = addres.number();
        this.complement = addres.complement();
    }

    public Address updateData(AddressData addres) {
        this.street = addres.street();
        this.district = addres.district();
        this.city = addres.city();
        this.number = addres.number();
        this.complement = addres.complement();
        return this;
    }
}
