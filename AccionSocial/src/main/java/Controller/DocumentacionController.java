package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import Entity.Documentacion;
import Entity.Legajo;
import Entity.Ubicacion;
import Service.InterDocumentacion;
import Service.InterLegajo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import java.time.LocalDate;

@Controller
public class DocumentacionController {

    @Autowired
    private InterDocumentacion documentacionService;
    
    @Autowired
    private InterLegajo legajoService;
    
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
    public String guardar(
            @ModelAttribute("documentacion") Documentacion documentacion,
            @RequestParam(value = "legajoId", required = false) Long legajoId,
            Model model) {
        
        if (documentacion.getEstado() == null || documentacion.getEstado().isEmpty()) {
            documentacion.setEstado("Pendiente");
        }
        
        if (documentacion.getFechaIngreso() == null) {
            documentacion.setFechaIngreso(LocalDate.now());
        }
        
        if (legajoId != null) {
            Optional<Legajo> legajoOp = legajoService.Busquedaporid(legajoId);
            if (legajoOp.isPresent()) {
                documentacion.setLegajo(legajoOp.get());
            }
        }
        
        if (documentacion.getTipoDocumento() != null && !documentacion.getTipoDocumento().trim().isEmpty()) {
            
            Ubicacion ubicacion;
            
            if (documentacion.getUbicacion() != null && documentacion.getUbicacion().getId() != null) {
                ubicacion = documentacion.getUbicacion();
            } else {
                ubicacion = new Ubicacion();
            }
            if (ubicacion.getArchivo() != null && !ubicacion.getArchivo().trim().isEmpty()) {
                ubicacion.setArchivo(ubicacion.getArchivo().trim());
            }

            
            documentacion.setUbicacion(ubicacion);
        }
        
        documentacionService.Guardar(documentacion);
        
        
       if (documentacion.getLegajo() != null) {
        return "redirect:/detallelegajo/" + documentacion.getLegajo().getId() + 
               "?mensaje=Documentación creada y asignada correctamente";
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
