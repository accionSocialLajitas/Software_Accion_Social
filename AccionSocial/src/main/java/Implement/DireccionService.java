package Implement;

import java.util.List;
import java.util.Optional;

import Entity.Direccion;
import Repository.DireccionRepository;
import Service.InterDireccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService implements InterDireccion {
    
    @Autowired
    private DireccionRepository DireccionRepository;

    @Override
    public void Guardar(Direccion direccion) {
        DireccionRepository.save(direccion);
    }

    @Override
    public Optional<Direccion> Busquedaporid(Long id) {
        return DireccionRepository.findById(id);
    }

    @Override
    public Direccion buscarDireccionPorLocalidad(String localidad) {
        return DireccionRepository.findByLocalidad(localidad);
    }

    @Override
    public void Eliminar(Direccion direccion) {
        DireccionRepository.delete(direccion);
    }

    @Override
    public void Editar(Direccion direccion) {
        DireccionRepository.save(direccion);
    }

    @Override
    public List<Direccion> getDirecciones() {
        return DireccionRepository.findAll();
    }
}
