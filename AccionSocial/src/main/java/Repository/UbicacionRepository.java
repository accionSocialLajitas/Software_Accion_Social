package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Entity.Ubicacion;


public interface UbicacionRepository extends JpaRepository<Ubicacion, Long>{
	
	Ubicacion findByArchivo(String archivo);

	Ubicacion findByCarpeta(String carpeta);

	Ubicacion findByFolio(String folio);

}
