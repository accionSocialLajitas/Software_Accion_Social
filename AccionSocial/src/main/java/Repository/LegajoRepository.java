package Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import Entity.Legajo;
import java.util.List;
import java.util.Optional;

@Repository
public interface LegajoRepository extends JpaRepository<Legajo, Long> {
    Legajo findByDni(String dni);
    Legajo findByDniAndActivoTrue(String dni);
    List<Legajo> findByActivoTrue();
    @EntityGraph(attributePaths = {"direccion"})
    Optional<Legajo> findWithRelationsById(Long id);
    @EntityGraph(attributePaths = {"direccion"})
    Optional<Legajo> findByIdAndActivoTrue(Long id);
}




