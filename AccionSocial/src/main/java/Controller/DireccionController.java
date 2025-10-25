package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import Entity.Direccion;
import Service.InterDireccion;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class DireccionController {

    @Autowired
    private InterDireccion DireccionService;
    
    @GetMapping("/mostrar-direcciones")
    public String DetallesDirecciones(Model model) {
        model.addAttribute("Direcciones", DireccionService.getDirecciones());
        return "Vistas/lista_direcciones";
    }
    
    @GetMapping("/formulario-direccion")
    public String GuardarDatosDireccion(Model model) {
        model.addAttribute("direccion", new Direccion());
        return "Vistas/formulario_direccion";
    }

    @PostMapping("/guardar-direccion")
    public String GuardarDireccion(Direccion direccion, Model model) {
        DireccionService.Guardar(direccion);
        return "redirect:/mostrar-direcciones";
    }
}
