package Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Entity.Turno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
}


