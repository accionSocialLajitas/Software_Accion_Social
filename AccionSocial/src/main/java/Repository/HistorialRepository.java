package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Entity.Historial;
import Entity.Legajo;
import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {
    List<Historial> findByLegajoOrderByFechaHoraDesc(Legajo legajo);
    List<Historial> findByLegajoIdOrderByFechaHoraDesc(Long legajoId);
}

