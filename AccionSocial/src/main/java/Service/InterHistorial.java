package Service;

import java.util.List;
import java.util.Optional;
import Entity.Historial;
import Entity.Legajo;

public interface InterHistorial {
    Historial Guardar(Historial historial);
    Optional<Historial> Busquedaporid(Long id);
    void Eliminar(Historial historial);
    void Editar(Historial historial);
    List<Historial> getHistorial();
    List<Historial> getHistorialPorLegajo(Legajo legajo);
    List<Historial> getHistorialPorLegajoId(Long legajoId);
}

