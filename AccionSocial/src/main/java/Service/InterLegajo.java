package Service;

import java.util.List;
import java.util.Optional;
import Entity.Legajo;

public interface InterLegajo {

	public void Guardar(Legajo legajo);
	
	public void Eliminar(Legajo legajo);
	
	public void Editar(Legajo legajo);
	
	public Optional<Legajo> Busquedaporid(Long id);
	
	public boolean existeDniDuplicado(String dni, Long id);
	
	public Legajo buscarLegajoPorDni(String dni);
	
	public List<Legajo> getLegajo();
	
	
	
}
