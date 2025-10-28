package Service;

import java.util.List;
import java.util.Optional;
import Entity.Ubicacion;

public interface InterUbicacion {
	
public void Guardar(Ubicacion Ubicacion);
    
    public void Eliminar(Ubicacion Ubicacion);
    
    public void Editar(Ubicacion Ubicacion);
    
    public Optional<Ubicacion> Busquedaporid(Long id);
    
    public boolean existeUbicacionDuplicado(String Ubicacion, Long id);
    
    public Ubicacion buscarUbicacionPorUbicacion(String Ubicacion);
    
    public List<Ubicacion> getUbicacion();

}
