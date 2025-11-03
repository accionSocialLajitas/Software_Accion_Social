package Implement;

import java.util.List;
import java.util.Optional;

import Entity.Turno;
import Repository.TurnoRepository;
import Service.InterTurno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoService implements InterTurno {
	
	@Autowired
    private TurnoRepository TurnoRepository;

	@Override
	public Turno Guardar(Turno turno) {
		return TurnoRepository.save(turno);
		
	}

	@Override
	public void Eliminar(Turno turno) {
		TurnoRepository.delete(turno);
		
	}

	@Override
	public void Editar(Turno turno) {
		TurnoRepository.save(turno);
		
	}

	@Override
	public Optional<Turno> Busquedaporid(Long id) {
		return Optional.empty();
	}

	@Override
	public List<Turno> getTurnos() {
		return TurnoRepository.findAll();
	}
	
	

	
}
