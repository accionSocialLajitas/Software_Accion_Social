package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import Entity.Ubicacion;
import Service.InterUbicacion;



@Controller
public class UbicacionController {
	
	@Autowired
    private InterUbicacion ubicacionService;
	
    @GetMapping("/mostrarlistaubicacion")
    public String detallesUbicacion(Model model) {
        model.addAttribute("Ubicacion", ubicacionService.getUbicacion());
        return "Vistas/lista_ubicacion";
    }
    
    @GetMapping("/formularioubicacion")  
    public String guardarDatos(Model model) {
    	model.addAttribute("ubicacion", new Ubicacion());
        return "Vistas/formulario_ubicacion";
    }

    @PostMapping("/guardarubicacion")
    public String guardar(Ubicacion ubicacion, Model model) {
   
        ubicacionService.Guardar(ubicacion);
        return "redirect:/mostrarlistaubicacion";
    }
    
    @GetMapping("/editarubicacion/{id}")
    public String editarUbicacion(@PathVariable Long id, Model model) {
        Ubicacion ubicacion = ubicacionService.Busquedaporid(id).orElse(null);
        if (ubicacion != null) {
            model.addAttribute("ubicacion", ubicacion);
            return "Vistas/formulario_ubicacion";
        }
        return "redirect:/mostrarlistaubicacion";
    }
    
    
    @GetMapping("/eliminarubicacion/{id}")
    public String eliminarUbicacion(@PathVariable Long id) {
        Ubicacion ubicacion = ubicacionService.Busquedaporid(id).orElse(null);
        if (ubicacion != null) {
            ubicacionService.Eliminar(ubicacion);
        }
        return "redirect:/mostrarlistaubicacion";
    }
}
