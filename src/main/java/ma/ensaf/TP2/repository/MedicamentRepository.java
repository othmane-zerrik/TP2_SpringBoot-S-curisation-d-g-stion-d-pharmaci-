package ma.ensaf.TP2.repository;

import ma.ensaf.TP2.model.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentRepository
        extends JpaRepository<Medicament, Long> {
}
