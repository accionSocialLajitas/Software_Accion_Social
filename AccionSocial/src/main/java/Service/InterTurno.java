package Service;

import java.util.List;
import java.util.Optional;
import Entity.Turno;

public interface InterTurno {
	
	public Turno Guardar(Turno turno);
    
    public void Eliminar(Turno turno);
    
    public void Editar(Turno turno);
    
    public Optional<Turno> Busquedaporid(Long id);
    
    public List<Turno> getTurnos();

}
