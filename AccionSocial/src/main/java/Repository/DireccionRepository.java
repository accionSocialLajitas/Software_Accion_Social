package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Entity.Direccion;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    Direccion findByLocalidad(String localidad);
}
