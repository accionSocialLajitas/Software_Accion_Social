package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import Entity.Legajo;
import Service.InterHistorial;
import Service.InterLegajo;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Controller
public class HistorialController {

    @Autowired
    private InterHistorial historialService;
    
    @Autowired
    private InterLegajo legajoService;
    
    @GetMapping("/historial/{legajoId}")
    public String mostrarHistorial(@PathVariable Long legajoId, Model model) {
        Optional<Legajo> legajoOp = legajoService.Busquedaporid(legajoId);
        if (legajoOp.isPresent()) {
            Legajo legajo = legajoOp.get();
            model.addAttribute("legajo", legajo);
            model.addAttribute("historial", historialService.getHistorialPorLegajoId(legajoId));
            return "Vistas/historial_legajo";
        }
        return "redirect:/mostrarlista";
    }
    
    @GetMapping("/historial/legajo/{legajoId}")
    public String verHistorialLegajo(@PathVariable Long legajoId, Model model) {
        return mostrarHistorial(legajoId, model);
    }
}

