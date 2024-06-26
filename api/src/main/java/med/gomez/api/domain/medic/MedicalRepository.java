package med.gomez.api.domain.medic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface MedicalRepository extends JpaRepository<Medic, Long> {

    Page<Medic> findByActiveTrue(Pageable pagination);
    @Query("""
    select m from Medico m 
    where m.active = 1 
    and m.specialty = :specialty 
    and m.id not in (
        select c.medico.id from Consulta c 
        where c.fecha = :fecha
    )
    order by function('RAND') 
    """)
    Medic selectMedicalSpecialtyOnDate(Specialty specialty, LocalDateTime fecha);
}
