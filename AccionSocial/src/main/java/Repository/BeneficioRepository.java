package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Entity.Beneficio;
import Entity.Legajo;
import java.util.List;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {
    Beneficio findByNombre(String nombre);
    List<Beneficio> findByLegajoIsNullAndEstadoNot(String estado);
    List<Beneficio> findByLegajoIsNullAndEstadoNotAndCantidadDisponibleGreaterThan(String estado, int cantidad);
    Beneficio findByLegajoAndNombre(Legajo legajo, String nombre);
    List<Beneficio> findByLegajo(Legajo legajo);
}
