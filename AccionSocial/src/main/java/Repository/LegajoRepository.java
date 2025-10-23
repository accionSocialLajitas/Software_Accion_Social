package Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Entity.Legajo;

@Repository
public interface LegajoRepository extends JpaRepository<Legajo, Long> {
    Legajo findByDni(String dni);
}




