package Implement;

import java.util.List;
import java.util.Optional;

import Entity.Beneficio;
import Repository.BeneficioRepository;
import Service.InterBeneficio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeneficioService implements InterBeneficio {
    
    @Autowired
    private BeneficioRepository beneficioRepository;

    @Override
    public void Guardar(Beneficio beneficio) {
        beneficioRepository.save(beneficio);
    }

    @Override
    public Optional<Beneficio> Busquedaporid(Long id) {
        return beneficioRepository.findById(id);
    }

    @Override
    public Beneficio buscarBeneficioPorNombre(String nombre) {
        return beneficioRepository.findByNombre(nombre);
    }

    @Override
    public void Eliminar(Beneficio beneficio) {
        beneficioRepository.delete(beneficio);
    }

    @Override
    public void Editar(Beneficio beneficio) {
        beneficioRepository.save(beneficio);
    }

    @Override
    public List<Beneficio> getBeneficio() {
        return beneficioRepository.findAll();
    }
}
