package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import Entity.Beneficio;
import Service.InterBeneficio;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

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
    
    @GetMapping("/editarbeneficios/{id}")
    public String editarBeneficio(@PathVariable Long id, Model model) {
        Optional<Beneficio> beneficioOp = beneficioService.Busquedaporid(id);
        if (beneficioOp.isPresent()) {
            model.addAttribute("beneficio", beneficioOp.get());
            return "Vistas/formulario_beneficio";
        }
        return "redirect:/mostrarlistabeneficios";
    }
    
    @GetMapping("/eliminarbeneficios/{id}")
    public String eliminarBeneficio(@PathVariable Long id) {
        Optional<Beneficio> beneficioOp = beneficioService.Busquedaporid(id);
        if (beneficioOp.isPresent()) {
            beneficioService.Eliminar(beneficioOp.get());
        }
        return "redirect:/mostrarlistabeneficios";
    }
}
