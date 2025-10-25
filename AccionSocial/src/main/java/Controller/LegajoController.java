package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import Entity.Legajo;
import Service.InterLegajo;
import org.springframework.beans.factory.annotation.Autowired;





@Controller
public class LegajoController {

	@Autowired
	private InterLegajo LegajoService;
	
	 @GetMapping("/mostrarlista")
	    public String DetallesPersonas(Model model) {
	        model.addAttribute("Legajo", LegajoService.getLegajo());
	        return "Vistas/lista_legajos";
	
	 }
	        @GetMapping("/formulario")  
	        public String GuardarDatos(Model model) {
	            Legajo legajo = new Legajo();
	            legajo.setEstado("Pendiente");
	            model.addAttribute("legajo", legajo);
	            return "Vistas/formulario_legajo";
	        }
	
	        @PostMapping("/guardar")
	        public String Guardar(Legajo legajo, Model model) {
	            Long id = legajo.getId();
	            if (LegajoService.existeDniDuplicado(legajo.getDni(), id)) {
	                model.addAttribute("error", "DNI ya existente");
	                
	                if (id == null || id == 0) {
	                    model.addAttribute("legajo", legajo);
	                    return "Vistas/formulario_legajo";
	                } else {
	                    model.addAttribute("legajo", legajo);
	                    return "redirect:/lista_legajos";
	                }
	            }
	            
	            LegajoService.Guardar(legajo);
	            return "redirect:/mostrarlista";
	        }
	        
	
}
