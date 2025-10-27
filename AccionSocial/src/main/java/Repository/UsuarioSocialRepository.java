package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Entity.UsuarioSocial;

@Repository
public interface UsuarioSocialRepository extends JpaRepository<UsuarioSocial, Long> {
    UsuarioSocial findByUsuario(String usuario);
}
