package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import Entity.Beneficio;
import Service.InterBeneficio;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class BeneficioController {

    @Autowired
    private InterBeneficio beneficioService;
    
    @GetMapping("/mostrarlistabeneficios")
    public String detallesBeneficios(Model model) {
        model.addAttribute("Beneficio", beneficioService.getBeneficio());
        return "Vistas/lista_beneficios";
    }
    
    @GetMapping("/formulariobeneficios")  
    public String guardarDatos(Model model) {
        Beneficio beneficio = new Beneficio();
        beneficio.setEstado("Activo");
        model.addAttribute("beneficio", beneficio);
        return "Vistas/formulario_beneficio";
    }

    @PostMapping("/guardarbeneficios")
    public String guardar(Beneficio beneficio, Model model) {
        beneficioService.Guardar(beneficio);
        return "redirect:/mostrarlistabeneficios";
    }
}
