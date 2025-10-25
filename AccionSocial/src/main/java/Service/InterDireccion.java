package Service;

import java.util.List;
import java.util.Optional;
import Entity.Direccion;

public interface InterDireccion {

    public void Guardar(Direccion direccion);
    
    public void Eliminar(Direccion direccion);
    
    public void Editar(Direccion direccion);
    
    public Optional<Direccion> Busquedaporid(Long id);
    
    public Direccion buscarDireccionPorLocalidad(String localidad);
    
    public List<Direccion> getDirecciones();
}
