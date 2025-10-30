package Service;

import java.util.List;
import java.util.Optional;
import Entity.Documentacion;

public interface InterDocumentacion {

    public void Guardar(Documentacion documentacion);
    
    public void Eliminar(Documentacion documentacion);
    
    public void Editar(Documentacion documentacion);
    
    public Optional<Documentacion> Busquedaporid(Long id);
    
    public Documentacion buscarDocumentacionPorNumero(String numero);
    
    public Documentacion buscarDocumentacionPorTipo(String tipoDocumento);
    
    public List<Documentacion> getDocumentacion();
}
