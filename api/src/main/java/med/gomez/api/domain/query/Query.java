package med.gomez.api.domain.query;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.gomez.api.domain.medic.Medic;
import med.gomez.api.domain.patient.Patient;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Query")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medic_id")
    private Medic medic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDateTime fecha;

    @Column(name = "Reason_Cancellation")
    @Enumerated(EnumType.STRING)
    private ReasonCancellation reasonCancellation;

    public Query (Medic medic,Patient patient,LocalDateTime fecha){
        this.medic=medic;
        this.patient=patient;
        this.fecha=fecha;
    }

    public void cancel (ReasonCancellation reasonCancellation){
        this.reasonCancellation = reasonCancellation;

    }

}
