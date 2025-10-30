package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

import Entity.Documentacion;
import Service.InterDocumentacion;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Controller
public class DocumentacionController {

    @Autowired
    private InterDocumentacion documentacionService;
    
    @GetMapping("/mostrarlistadocumentacion")
    public String detallesDocumentacion(Model model) {
        model.addAttribute("Documentacion", documentacionService.getDocumentacion());
        return "Vistas/lista_documentacion";
    }
    
    @GetMapping("/formulariodocumentacion")  
    public String guardarDatos(Model model) {
        Documentacion documentacion = new Documentacion();
        model.addAttribute("documentacion", documentacion);
        return "Vistas/formulario_documentacion";
    }

    @PostMapping("/guardardocumentacion")
    public String guardar(Documentacion documentacion, Model model) {
        documentacionService.Guardar(documentacion);
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
