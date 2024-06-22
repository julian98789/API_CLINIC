package med.gomez.api.addres;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.gomez.api.medic.updateMedicalData;

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

    public Addres updateData(AddressData addres) {
        this.street = addres.street();
        this.district = addres.district();
        this.city = addres.city();
        this.number = addres.number();
        this.complement = addres.complement();
        return this;
    }
}
