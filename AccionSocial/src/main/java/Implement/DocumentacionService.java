package Implement;

import java.util.List;
import java.util.Optional;

import Entity.Documentacion;
import Repository.DocumentacionRepository;
import Service.InterDocumentacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentacionService implements InterDocumentacion {
    
    @Autowired
    private DocumentacionRepository documentacionRepository;

    @Override
    public void Guardar(Documentacion documentacion) {
        documentacionRepository.save(documentacion);
    }

    @Override
    public Optional<Documentacion> Busquedaporid(Long id) {
        return documentacionRepository.findById(id);
    }

    @Override
    public Documentacion buscarDocumentacionPorNumero(String numero) {
        return documentacionRepository.findByNumero(numero);
    }

    @Override
    public Documentacion buscarDocumentacionPorTipo(String tipoDocumento) {
        return documentacionRepository.findByTipoDocumento(tipoDocumento);
    }

    @Override
    public void Eliminar(Documentacion documentacion) {
        documentacionRepository.delete(documentacion);
    }

    @Override
    public void Editar(Documentacion documentacion) {
        documentacionRepository.save(documentacion);
    }

    @Override
    public List<Documentacion> getDocumentacion() {
        return documentacionRepository.findAll();
    }
}
