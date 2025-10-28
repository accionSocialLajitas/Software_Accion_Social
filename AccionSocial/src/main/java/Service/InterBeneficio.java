package Service;

import java.util.List;
import java.util.Optional;
import Entity.Beneficio;

public interface InterBeneficio {

    public void Guardar(Beneficio beneficio);
    
    public void Eliminar(Beneficio beneficio);
    
    public void Editar(Beneficio beneficio);
    
    public Optional<Beneficio> Busquedaporid(Long id);
    
    public Beneficio buscarBeneficioPorNombre(String nombre);
    
    public List<Beneficio> getBeneficio();
}
