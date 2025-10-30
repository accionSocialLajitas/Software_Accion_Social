package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Entity.Documentacion;

@Repository
public interface DocumentacionRepository extends JpaRepository<Documentacion, Long> {
    Documentacion findByNumero(String numero);
    Documentacion findByTipoDocumento(String tipoDocumento);
}
