package Implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Entity.Ubicacion;
import Repository.UbicacionRepository;
import Service.InterUbicacion;

@Service
public class UbicacionService implements InterUbicacion {
	
	@Autowired
    private UbicacionRepository ubicacionRepository;

	@Override
	public void Guardar(Ubicacion ubicacion) {
		ubicacionRepository.save(ubicacion);
		
	}

	@Override
	public void Eliminar(Ubicacion ubicacion) {
		ubicacionRepository.delete(ubicacion);
		
	}

	@Override
	public void Editar(Ubicacion ubicacion) {
		ubicacionRepository.save(ubicacion);
		
	}

	@Override
	public Optional<Ubicacion> Busquedaporid(Long id) {
		return ubicacionRepository.findById(id);
	}

	@Override
	public boolean existeUbicacionDuplicado(String archivo, Long id) {
		Ubicacion ubicacionExistente = ubicacionRepository.findByArchivo(archivo);
	    if (ubicacionExistente == null) {
	        return false;
	    }
	    return id == null || !ubicacionExistente.getId().equals(id);
	}

	@Override
	public Ubicacion buscarUbicacionPorUbicacion(String archivo) {
		return ubicacionRepository.findByArchivo(archivo);
	}

	@Override
	public List<Ubicacion> getUbicacion() {
		return ubicacionRepository.findAll();
	}

}
