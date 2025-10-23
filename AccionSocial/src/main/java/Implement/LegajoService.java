package Implement;

import java.util.List;
import java.util.Optional;

import Entity.Legajo;
import Repository.LegajoRepository;
import Service.InterLegajo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegajoService implements InterLegajo {
    
    @Autowired
    private LegajoRepository LegajoRepository;


@Override
public void Guardar(Legajo legajo) {
	LegajoRepository.save(legajo);
}


@Override
public Optional<Legajo> Busquedaporid(Long id) {
    return LegajoRepository.findById(id);
}

@Override
public Legajo buscarLegajoPorDni(String dni) {
    return LegajoRepository.findByDni(dni);
}

@Override
public boolean existeDniDuplicado(String dni, Long id) {
    Legajo legajoExistente = LegajoRepository.findByDni(dni);
    if (legajoExistente == null) {
        return false;
    }
    return id == null || !legajoExistente.getId().equals(id);
}



@Override
public void Eliminar(Legajo legajo) {
    LegajoRepository.delete(legajo);
}

@Override
public void Editar(Legajo legajo) {
    LegajoRepository.save(legajo);
}


@Override
public List<Legajo> getLegajo() {
	return LegajoRepository.findAll();

}




}
