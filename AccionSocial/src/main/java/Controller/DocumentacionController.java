package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

import Entity.Documentacion;
import Entity.Legajo;
import Entity.Historial;
import Entity.Ubicacion;
import Service.InterDocumentacion;
import Service.InterLegajo;
import Service.InterHistorial;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.time.LocalDateTime;

@Controller
public class DocumentacionController {

    @Autowired
    private InterDocumentacion documentacionService;
    
    @Autowired
    private InterLegajo legajoService;
    
    @Autowired
    private InterHistorial historialService;
    
    @GetMapping("/mostrarlistadocumentacion")
    public String detallesDocumentacion(Model model) {
        model.addAttribute("Documentacion", documentacionService.getDocumentacion());
        return "Vistas/lista_documentacion";
    }
    
    @GetMapping("/formulariodocumentacion")  
    public String guardarDatos(@org.springframework.web.bind.annotation.RequestParam(required = false) Long legajoId, Model model) {
        Documentacion documentacion = new Documentacion();
        documentacion.setEstado("Pendiente");
        
       
        if (legajoId != null) {
            Optional<Legajo> legajoOp = legajoService.Busquedaporid(legajoId);
            if (legajoOp.isPresent()) {
                documentacion.setLegajo(legajoOp.get());
                model.addAttribute("legajo", legajoOp.get());
            }
        }
        
        model.addAttribute("documentacion", documentacion);
        model.addAttribute("legajoId", legajoId);
        return "Vistas/formulario_documentacion";
    }

    @PostMapping("/guardardocumentacion")
    public String guardar(Documentacion documentacion, 
                         @org.springframework.web.bind.annotation.RequestParam(required = false) Long legajoId,
                         @org.springframework.web.bind.annotation.RequestParam(required = false) String archivo,
                         @org.springframework.web.bind.annotation.RequestParam(required = false) String carpeta,
                         @org.springframework.web.bind.annotation.RequestParam(required = false) String folio,
                         Model model) {
        
        
        if (documentacion.getEstado() == null || documentacion.getEstado().isEmpty()) {
            documentacion.setEstado("Pendiente");
        }
        
       
        if (legajoId != null) {
            Optional<Legajo> legajoOp = legajoService.Busquedaporid(legajoId);
            if (legajoOp.isPresent()) {
                documentacion.setLegajo(legajoOp.get());
            }
        }
        
       
        if ((archivo != null && !archivo.trim().isEmpty()) || 
            (carpeta != null && !carpeta.trim().isEmpty()) || 
            (folio != null && !folio.trim().isEmpty())) {
            
            Ubicacion ubicacion;
            
           
            if (documentacion.getUbicacion() != null && documentacion.getUbicacion().getId() != null) {
                ubicacion = documentacion.getUbicacion();
            } else {
               
                ubicacion = new Ubicacion();
            }
            
            if (archivo != null && !archivo.trim().isEmpty()) {
                ubicacion.setArchivo(archivo.trim());
            }
            if (carpeta != null && !carpeta.trim().isEmpty()) {
                ubicacion.setCarpeta(carpeta.trim());
            }
            if (folio != null && !folio.trim().isEmpty()) {
                ubicacion.setFolio(folio.trim());
            }
            
            documentacion.setUbicacion(ubicacion);
        }
        
        documentacionService.Guardar(documentacion);
        
        
        if (documentacion.getLegajo() != null) {
            Legajo legajo = documentacion.getLegajo();
            
           
            Historial historial = new Historial();
            historial.setLegajo(legajo);
            historial.setTipoEvento("DOCUMENTACION_ASIGNADA");
            historial.setFechaHora(LocalDateTime.now());
            String descripcionHistorial = "Documentación '" + documentacion.getTipoDocumento() + 
                                         "' (Nº " + documentacion.getNumero() + 
                                         ") creada y asignada al legajo. Estado: " + documentacion.getEstado();
            if (documentacion.getFechaIngreso() != null) {
                descripcionHistorial += ". Fecha de ingreso: " + documentacion.getFechaIngreso();
            }
            if (documentacion.getUbicacion() != null) {
                descripcionHistorial += ". Ubicación física: ";
                boolean hayDatos = false;
                if (documentacion.getUbicacion().getArchivo() != null && !documentacion.getUbicacion().getArchivo().isEmpty()) {
                    descripcionHistorial += "Archivo: " + documentacion.getUbicacion().getArchivo();
                    hayDatos = true;
                }
                if (documentacion.getUbicacion().getCarpeta() != null && !documentacion.getUbicacion().getCarpeta().isEmpty()) {
                    if (hayDatos) descripcionHistorial += ", ";
                    descripcionHistorial += "Carpeta: " + documentacion.getUbicacion().getCarpeta();
                    hayDatos = true;
                }
                if (documentacion.getUbicacion().getFolio() != null && !documentacion.getUbicacion().getFolio().isEmpty()) {
                    if (hayDatos) descripcionHistorial += ", ";
                    descripcionHistorial += "Folio: " + documentacion.getUbicacion().getFolio();
                }
            }
            historial.setDescripcion(descripcionHistorial);
            historialService.Guardar(historial);
            
            return "redirect:/detallelegajo/" + legajo.getId() + "?mensaje=Documentación creada y asignada correctamente";
        }
        
        return "redirect:/mostrarlistadocumentacion";
    }
    
    @GetMapping("/editardocumentacion/{id}")
    public String editarDocumentacion(@PathVariable Long id, Model model) {
        Optional<Documentacion> documentacionOp = documentacionService.Busquedaporid(id);
        if (documentacionOp.isPresent()) {
            model.addAttribute("documentacion", documentacionOp.get());
            return "Vistas/formulario_documentacion";
        }
        return "redirect:/mostrarlistadocumentacion";
    }
    
    @GetMapping("/eliminardocumentacion/{id}")
    public String eliminarDocumentacion(@PathVariable Long id) {
        Optional<Documentacion> documentacionOp = documentacionService.Busquedaporid(id);
        if (documentacionOp.isPresent()) {
            documentacionService.Eliminar(documentacionOp.get());
        }
        return "redirect:/mostrarlistadocumentacion";
    }
}
