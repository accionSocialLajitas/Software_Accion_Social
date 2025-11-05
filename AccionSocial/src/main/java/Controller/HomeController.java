package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Entity.Beneficio;
import Service.InterLegajo;
import Service.InterBeneficio;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class HomeController {

    @Autowired
    private InterLegajo legajoService;
    
    @Autowired
    private InterBeneficio beneficioService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Inicio");
        
       
        try {
            long totalLegajos = legajoService.getLegajo().size();
        long totalBeneficios = 0;
            for (Beneficio beneficio : beneficioService.getBeneficio()) {
            if (beneficio.getLegajo() == null) {
                totalBeneficios++;
            }
        }

            
            model.addAttribute("totalLegajos", totalLegajos);
            model.addAttribute("totalBeneficios", totalBeneficios);
        } catch (Exception e) {
            model.addAttribute("totalLegajos", 0);
            model.addAttribute("totalBeneficios", 0);
        }
        
        return "Vistas/inicio";
    }
}

