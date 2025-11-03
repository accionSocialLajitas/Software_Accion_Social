package Implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import Entity.Documentacion;
import Entity.Historial;
import Entity.Legajo;
import Entity.Ubicacion;
import Repository.DocumentacionRepository;
import Service.InterDocumentacion;
import Service.InterHistorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentacionService implements InterDocumentacion {
    
    @Autowired
    private DocumentacionRepository documentacionRepository;
    
    @Autowired
    private InterHistorial historialService;

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
    
    /**
     * Solicita un documento para un legajo (crea el documento con estado SOLICITADO) y registra en historial
     */
    public void solicitarDocumento(Documentacion documentacion, Legajo legajo) {
        documentacion.setLegajo(legajo);
        documentacion.setEstado("SOLICITADO");
        documentacionRepository.save(documentacion);
        
        
        Historial historial = new Historial();
        historial.setLegajo(legajo);
        historial.setTipoEvento("DOCUMENTO_SOLICITADO");
        historial.setFechaHora(LocalDateTime.now());
        historial.setDescripcion("Documento solicitado: " + documentacion.getTipoDocumento() + 
                                (documentacion.getNumero() != null ? " - Número: " + documentacion.getNumero() : ""));
        historialService.Guardar(historial);
    }
    
    public void guardarDocumento(Documentacion documentacion) {
        if (documentacion.getId() != null) {
            Optional<Documentacion> docOp = documentacionRepository.findById(documentacion.getId());
            if (docOp.isPresent()) {
                Documentacion docExistente = docOp.get();
                docExistente.setEstado("GUARDADO");
                if (documentacion.getUbicacion() != null) {
                    docExistente.setUbicacion(documentacion.getUbicacion());
                }
                if (documentacion.getFechaIngreso() != null) {
                    docExistente.setFechaIngreso(documentacion.getFechaIngreso());
                }
                documentacionRepository.save(docExistente);
                
               
                Historial historial = new Historial();
                historial.setLegajo(docExistente.getLegajo());
                historial.setTipoEvento("DOCUMENTO_GUARDADO");
                historial.setFechaHora(LocalDateTime.now());
                String descripcion = "Documento guardado: " + docExistente.getTipoDocumento();
                if (docExistente.getNumero() != null) {
                    descripcion += " - Número: " + docExistente.getNumero();
                }
                if (docExistente.getUbicacion() != null) {
                    Ubicacion ubicacion = docExistente.getUbicacion();
                    descripcion += " - Ubicación física: ";
                    boolean hayDatos = false;
                    if (ubicacion.getArchivo() != null && !ubicacion.getArchivo().isEmpty()) {
                        descripcion += "Archivo: " + ubicacion.getArchivo();
                        hayDatos = true;
                    }
                    if (ubicacion.getCarpeta() != null && !ubicacion.getCarpeta().isEmpty()) {
                        if (hayDatos) descripcion += ", ";
                        descripcion += "Carpeta: " + ubicacion.getCarpeta();
                        hayDatos = true;
                    }
                    if (ubicacion.getFolio() != null && !ubicacion.getFolio().isEmpty()) {
                        if (hayDatos) descripcion += ", ";
                        descripcion += "Folio: " + ubicacion.getFolio();
                    }
                }
                if (docExistente.getFechaIngreso() != null) {
                    descripcion += " - Fecha de ingreso: " + docExistente.getFechaIngreso().toString();
                }
                historial.setDescripcion(descripcion);
                historialService.Guardar(historial);
            }
        }
    }
}
