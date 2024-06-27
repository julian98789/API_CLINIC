package med.gomez.api.domain.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {

    Boolean existsByPatientIdAndFechaBetween(Long idPatient, LocalDateTime firstSchedule, LocalDateTime lastSchedule);

    Boolean existsByMedicIdAndFecha(Long idMedic, LocalDateTime fecha);
}
