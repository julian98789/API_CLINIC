package med.gomez.api.domain.medic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MedicalRepository extends JpaRepository<Medic, Long> {

    Page<Medic> findByActiveTrue(Pageable pagination);
}
