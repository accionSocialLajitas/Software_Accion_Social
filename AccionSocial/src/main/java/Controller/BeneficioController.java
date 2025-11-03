package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
<<<<<<< HEAD

import org.springframework.web.bind.annotation.RequestParam;
=======
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestParam;
=======
>>>>>>> 2f9a6ecf3d4295036cfc247a770cdd6075c57955
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 51cfce491bb69e371c163b35b9dec7a3ab7648c9

import Entity.Beneficio;
import Service.InterBeneficio;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Controller
public class BeneficioController {

    @Autowired
    private InterBeneficio beneficioService;
    
    @GetMapping("/mostrarlistabeneficios")
    public String detallesBeneficios(Model model) {
       
        List<Beneficio> todosBeneficios = beneficioService.getBeneficio();
        List<Beneficio> beneficiosRegistrados = todosBeneficios.stream()
            .filter(b -> b.getLegajo() == null) 
            .collect(java.util.stream.Collectors.toList());
        model.addAttribute("Beneficio", beneficiosRegistrados);
        return "Vistas/lista_beneficios";
    }
    

    
    @GetMapping("/formulariobeneficios")  
    public String guardarDatos(@RequestParam (required = false) String tipo, Model model) {
        Beneficio beneficio = new Beneficio();
        beneficio.setEstado("Activo");
        model.addAttribute("beneficio", beneficio);
        
        
        if (tipo == null) {
            tipo = "cantidad";
        }
        beneficio.setTipoBeneficio(tipo);
        
        model.addAttribute("beneficio", beneficio);
        model.addAttribute("tipo", tipo); 
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
