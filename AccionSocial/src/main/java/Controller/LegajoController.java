package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import Entity.Legajo;
import Entity.Turno;
import Entity.Direccion;
import Service.InterDireccion;
import Service.InterLegajo;
import Service.InterTurno;

import org.springframework.beans.factory.annotation.Autowired;





@Controller
@SessionAttributes({"legajo", "direccion","turno"})
public class LegajoController {

	@Autowired
	private InterLegajo LegajoService;
	@Autowired
	private InterDireccion DireccionService;
	@Autowired
	private InterTurno TurnoService;
	
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
	
	        @PostMapping("/guardar-paso1")
	        public String guardarPaso1(Legajo legajo, Model model) {
	            Long id = legajo.getId();
	            if (LegajoService.existeDniDuplicado(legajo.getDni(), id)) {
	                model.addAttribute("error", "DNI ya existente");
	                
	                if (id == null || id == 0) {
	                    model.addAttribute("legajo", legajo);
	                    return "Vistas/formulario_legajo";
	                }
	                
	                
            	}
	            
	            
	            
	            //LegajoService.Guardar(legajo);
	            model.addAttribute("legajo", legajo);
	            return "redirect:/formulario/direccion";
	        }
	        
	        @GetMapping("/formulario/direccion")
	        public String paso2(Model model) {
	            
	        	model.addAttribute("direccion", new Direccion());
	            
	            return "Vistas/formulario_direccion";
	        }
	        
	        @PostMapping("/guardar-paso2")
	        public String guardarPaso2(Direccion direccion, Model model) {
	            model.addAttribute("direccion", direccion);
	            return "redirect:/formulario/aprobacion";
	        }
	        
	        @GetMapping("/formulario/aprobacion")
	        public String pasoAprobacion(Legajo legajo, Direccion direccion, Model model) {
	            
	            return "Vistas/formulario_aprobacion";
	        }
	        
	        @PostMapping("/aprobacion-inmediata")
	        public String aprobacionInmediata(Legajo legajo, Direccion direccion, Model model) {

	            legajo.setEstado("Aprobado");
	            model.addAttribute("legajo", legajo);
	            
	            return "redirect:/confirmacion/aprobacion";
	        }
	        
	        @PostMapping("/programar-turno")
	        public String programarTurno(Legajo legajo, Direccion direccion, Model model) {
	        	
	        	Turno turno = new Turno();
	            model.addAttribute("turno", turno);

	            return "Vistas/formulario_turno";
	        }
	        
	        @PostMapping("/guardar-turno")
	        public String guardarTurno(Turno turno, Legajo legajo, Direccion direccion, Model model) {
	            
	            model.addAttribute("turno", turno);
	            return "redirect:/confirmacion/turno";
	        }
	        
	        @GetMapping("/confirmacion/aprobacion")
	        public String confirmacionAprobacion(Legajo legajo, Direccion direccion, Model model) {
	            
	            return "Vistas/confirmacion_aprobacion";
	        }
	        
	        @GetMapping("/confirmacion/turno")
	        public String confirmacionTurno(Legajo legajo, Direccion direccion, Turno turno, Model model) {
	            
	            return "Vistas/confirmacion_turno";
	        }
	        
	        @PostMapping("/guardar-todo-aprobacion")
	        public String guardarTodoAprobacion(Legajo legajo, Direccion direccion, SessionStatus status) {


	            legajo.setDireccion(direccion);
	          
	            LegajoService.Guardar(legajo);
	            
	            status.setComplete();
	            
	            return "redirect:/exito?tipo=aprobacion";
	        }
	        
	        @PostMapping("/guardar-todo-turno")
	        public String guardarTodoTurno(Legajo legajo, Direccion direccion, Turno turno, SessionStatus status) {

	            legajo.setDireccion(direccion);

	            Legajo legajoGuardado = LegajoService.Guardar(legajo);


	            turno.setLegajo(legajoGuardado);
	            turno.setDireccion(direccion);

	            Turno turnoGuardado = TurnoService.Guardar(turno);
	            
	            status.setComplete();
	            
	            return "redirect:/exito?tipo=turno";
	        }
	        
	        @GetMapping("/exito")
	        public String exito(String tipo, Model model) {
	            model.addAttribute("tipo", tipo);
	            return "Vistas/exito";
	        }
	        
	        

	       
	
}
