package Implement;

import java.util.List;
import java.util.Optional;

import Entity.Historial;
import Entity.Legajo;
import Repository.HistorialRepository;
import Service.InterHistorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistorialService implements InterHistorial {
    
    @Autowired
    private HistorialRepository historialRepository;

    @Override
    public Historial Guardar(Historial historial) {
        return historialRepository.save(historial);
    }

    @Override
    public Optional<Historial> Busquedaporid(Long id) {
        return historialRepository.findById(id);
    }

    @Override
    public void Eliminar(Historial historial) {
        historialRepository.delete(historial);
    }

    @Override
    public void Editar(Historial historial) {
        historialRepository.save(historial);
    }

    @Override
    public List<Historial> getHistorial() {
        return historialRepository.findAll();
    }

    @Override
    public List<Historial> getHistorialPorLegajo(Legajo legajo) {
        return historialRepository.findByLegajoOrderByFechaHoraDesc(legajo);
    }

    @Override
    public List<Historial> getHistorialPorLegajoId(Long legajoId) {
        return historialRepository.findByLegajoIdOrderByFechaHoraDesc(legajoId);
    }
}

