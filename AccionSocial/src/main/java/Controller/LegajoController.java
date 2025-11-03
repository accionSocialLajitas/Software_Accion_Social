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
import Entity.Beneficio;
import Entity.Documentacion;
import Entity.Historial;
import Service.InterLegajo;
import Service.InterTurno;
import Service.InterBeneficio;
import Service.InterDocumentacion;
import Service.InterHistorial;
import Implement.BeneficioService;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import java.util.List;





@Controller
@SessionAttributes({"legajo", "direccion","turno"})
public class LegajoController {

	@Autowired
	private InterLegajo LegajoService;
	@Autowired
	private InterTurno TurnoService;
	@Autowired
	private InterBeneficio BeneficioService;
	@Autowired
	private InterDocumentacion DocumentacionService;
	@Autowired
	private InterHistorial HistorialService;
	@Autowired
	private BeneficioService beneficioServiceImpl;
	
	 @GetMapping("/mostrarlista")
	    public String DetallesPersonas(@RequestParam(required = false) String dni, Model model) {
	        if (dni != null && !dni.trim().isEmpty()) {
	            // Buscar por DNI
	            Legajo legajoEncontrado = LegajoService.buscarLegajoPorDni(dni.trim());
	            if (legajoEncontrado != null) {
	                model.addAttribute("Legajo", List.of(legajoEncontrado));
	                model.addAttribute("dniBuscado", dni.trim());
	            } else {
	                model.addAttribute("Legajo", List.of());
	                model.addAttribute("dniBuscado", dni.trim());
	                model.addAttribute("mensaje", "No se encontró ningún legajo con el DNI: " + dni.trim());
	            }
	        } else {
	            // Mostrar todos los legajos
	            model.addAttribute("Legajo", LegajoService.getLegajo());
	        }
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
        public String paso2(Legajo legajo, Model model) {
            // Si el legajo tiene una dirección existente (estamos editando), usarla
            Direccion direccion = (legajo != null && legajo.getDireccion() != null) 
                ? legajo.getDireccion() 
                : new Direccion();
            
        	model.addAttribute("direccion", direccion);
            if (legajo != null) {
                model.addAttribute("legajo", legajo);
            }
	            
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
            // Si el legajo tiene ID y dirección existente, estamos editando
            if (legajo.getId() != null && legajo.getDireccion() != null) {
                // Si no se proporciona dirección nueva o está vacía, mantener la existente
                if (direccion == null || (direccion.getCalle() == null || direccion.getCalle().isEmpty())) {
                    // Mantener la dirección existente - no hacer nada
                    direccion = legajo.getDireccion();
                } else {
                    // Actualizar la dirección existente con los nuevos valores
                    Direccion direccionExistente = legajo.getDireccion();
                    direccionExistente.setCalle(direccion.getCalle());
                    direccionExistente.setLocalidad(direccion.getLocalidad());
                    direccionExistente.setNumero_casa(direccion.getNumero_casa());
                    direccion = direccionExistente;
                }
            }
            
            // Asignar dirección al legajo
	            legajo.setDireccion(direccion);
	          
            LegajoService.Guardar(legajo);
	            
	            status.setComplete();
	            
            return "redirect:/mostrarlista";
	        }
	        
	        @PostMapping("/guardar-todo-turno")
	        public String guardarTodoTurno(Legajo legajo, Direccion direccion, Turno turno, SessionStatus status) {
            // Si el legajo tiene ID y dirección existente, estamos editando
            if (legajo.getId() != null && legajo.getDireccion() != null) {
                // Si no se proporciona dirección nueva o está vacía, mantener la existente
                if (direccion == null || (direccion.getCalle() == null || direccion.getCalle().isEmpty())) {
                    // Mantener la dirección existente
                    direccion = legajo.getDireccion();
                } else {
                    // Actualizar la dirección existente con los nuevos valores
                    Direccion direccionExistente = legajo.getDireccion();
                    direccionExistente.setCalle(direccion.getCalle());
                    direccionExistente.setLocalidad(direccion.getLocalidad());
                    direccionExistente.setNumero_casa(direccion.getNumero_casa());
                    direccion = direccionExistente;
                }
            }
            
            // Asignar dirección al legajo
	            legajo.setDireccion(direccion);

            Legajo legajoGuardado = LegajoService.Guardar(legajo);

	            turno.setLegajo(legajoGuardado);
	            turno.setDireccion(direccion);

            TurnoService.Guardar(turno);
	            
	            status.setComplete();
	            
            return "redirect:/mostrarlista";
	        }
	        
	        @GetMapping("/exito")
	        public String exito(String tipo, Model model) {
	            model.addAttribute("tipo", tipo);
	            return "Vistas/exito";
	        }
	        
	        @GetMapping("/detallelegajo/{id}")
	        public String detalleLegajo(@PathVariable Long id, Model model) {
	            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(id);
	            if (legajoOp.isPresent()) {
	                Legajo legajo = legajoOp.get();
	                // Forzar carga de relaciones lazy de forma individual para evitar MultipleBagFetchException
	                if (legajo.getBeneficios() != null) {
	                    legajo.getBeneficios().size(); // Carga beneficios
	                }
	                if (legajo.getDocumentaciones() != null) {
	                    legajo.getDocumentaciones().size(); // Carga documentaciones
	                }
	                model.addAttribute("legajo", legajo);
	                return "Vistas/detalle_legajo";
	            }
	            return "redirect:/mostrarlista";
	        }
	        
	        @GetMapping("/asignarbeneficio/{id}")
	        public String mostrarAsignarBeneficio(@PathVariable Long id, Model model) {
	            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(id);
	            if (legajoOp.isPresent()) {
	                Legajo legajo = legajoOp.get();
	                model.addAttribute("legajo", legajo);
	                // Obtener beneficios disponibles (beneficios originales/plantilla sin asignar)
	                // Un beneficio disponible debe ser un beneficio "plantilla" que:
	                // 1. No tenga legajo asignado (es el original/plantilla)
	                // 2. No esté inactivo
	                // 3. No tenga estado "Asignado" (ese es solo para copias asignadas a legajos)
	                // 4. Dependiendo del tipo:
	                //    - Tipo "cantidad": debe tener cantidadDisponible > 0
	                //    - Tipo "fondos" o "monto": debe tener monto > 0
	                List<Beneficio> todosBeneficios = BeneficioService.getBeneficio();
	                List<Beneficio> beneficiosDisponibles = todosBeneficios.stream()
	                    .filter(b -> {
	                        // Excluir si tiene legajo asignado (son copias asignadas)
	                        if (b.getLegajo() != null) {
	                            return false;
	                        }
	                        
	                        // Excluir si está inactivo
	                        if (b.getEstado() != null && "Inactivo".equals(b.getEstado())) {
	                            return false;
	                        }
	                        
	                        // Excluir si tiene estado "Asignado" (son copias)
	                        if (b.getEstado() != null && "Asignado".equals(b.getEstado())) {
	                            return false;
	                        }
	                        
	                        // Verificar disponibilidad según el tipo de beneficio
	                        String tipoBeneficio = b.getTipoBeneficio();
					        
					        if (tipoBeneficio != null && ("fondos".equals(tipoBeneficio) || "monto".equals(tipoBeneficio))) {
					            // Beneficio de tipo monto/fondos: debe tener monto > 0
					            if (b.getMonto() == null || b.getMonto() <= 0) {
					                return false;
					            }
					        } else {
					            // Beneficio de tipo cantidad: debe tener cantidadDisponible > 0
					            if (b.getCantidadDisponible() <= 0) {
					                return false;
					            }
					        }
	                        
	                        // Si pasa todos los filtros, es un beneficio disponible
	                        return true;
	                    })
	                    .collect(java.util.stream.Collectors.toList());
	                model.addAttribute("beneficiosDisponibles", beneficiosDisponibles);
	                return "Vistas/asignar_beneficio";
	            }
	            return "redirect:/mostrarlista";
	        }
	        
        @PostMapping("/asignarbeneficio/{legajoId}")
        public String asignarBeneficio(@PathVariable Long legajoId, 
                                       @RequestParam Long beneficioId,
                                       @RequestParam(required = false) Integer cantidad,
                                       @RequestParam(required = false) Double monto,
                                       Model model) {
            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(legajoId);
            Optional<Beneficio> beneficioOp = BeneficioService.Busquedaporid(beneficioId);
            
            if (legajoOp.isPresent() && beneficioOp.isPresent()) {
                try {

                    String tipoBeneficio = beneficioOp.get().getTipoBeneficio();
                    boolean esBeneficioMonto = tipoBeneficio != null && ("fondos".equals(tipoBeneficio) || "monto".equals(tipoBeneficio));
                    if (esBeneficioMonto) {
                        if (monto == null || monto <= 0) {
                            throw new RuntimeException("Debe especificar un monto mayor a 0 para asignar este beneficio");
                        }
                        cantidad = 1;
                    } else {
                        if (cantidad == null || cantidad <= 0) {
                            throw new RuntimeException("Debe especificar una cantidad mayor a 0 para asignar este beneficio");
                        }
                    }
                    
                    beneficioServiceImpl.asignarBeneficioALegajo(beneficioOp.get(), legajoOp.get(), cantidad, monto);
                    return "redirect:/detallelegajo/" + legajoId + "?mensaje=Beneficio asignado correctamente";
                } catch (RuntimeException e) {
                    model.addAttribute("error", e.getMessage());
                    model.addAttribute("legajo", legajoOp.get());
                    // Recargar beneficios disponibles
                    List<Beneficio> todosBeneficios = BeneficioService.getBeneficio();
                    List<Beneficio> beneficiosDisponibles = todosBeneficios.stream()
                        .filter(b -> {
                            if (b.getLegajo() != null) return false;
                            if (b.getEstado() != null && "Inactivo".equals(b.getEstado())) return false;
                            if (b.getEstado() != null && "Asignado".equals(b.getEstado())) return false;
                            
                            String tipoBeneficio = b.getTipoBeneficio();
                            if (tipoBeneficio != null && ("fondos".equals(tipoBeneficio) || "monto".equals(tipoBeneficio))) {
                                if (b.getMonto() == null || b.getMonto() <= 0) return false;
                            } else {
                                if (b.getCantidadDisponible() <= 0) return false;
                            }
                            return true;
                        })
                        .collect(java.util.stream.Collectors.toList());
                    model.addAttribute("beneficiosDisponibles", beneficiosDisponibles);
                    return "Vistas/asignar_beneficio";
                }
            }
            return "redirect:/mostrarlista";
        }
	        
	        @GetMapping("/editarlegajo/{id}")
	        public String editarLegajo(@PathVariable Long id, Model model) {
	            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(id);
	            if (legajoOp.isPresent()) {
	                model.addAttribute("legajo", legajoOp.get());
	                return "Vistas/formulario_legajo";
	            }
	            return "redirect:/mostrarlista";
	        }
	        
	        @GetMapping("/asignardocumentacion/{id}")
	        public String mostrarAsignarDocumentacion(@PathVariable Long id, Model model) {
	            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(id);
	            if (legajoOp.isPresent()) {
	                Legajo legajo = legajoOp.get();
	                model.addAttribute("legajo", legajo);
	                // Obtener documentaciones disponibles (sin legajo asignado)
	                List<Documentacion> todasDocumentaciones = DocumentacionService.getDocumentacion();
	                List<Documentacion> documentacionesDisponibles = todasDocumentaciones.stream()
	                    .filter(d -> d.getLegajo() == null)
	                    .collect(java.util.stream.Collectors.toList());
	                model.addAttribute("documentacionesDisponibles", documentacionesDisponibles);
	                return "Vistas/asignar_documentacion";
	            }
	            return "redirect:/mostrarlista";
	        }
	        
	        @PostMapping("/asignardocumentacion/{legajoId}")
	        public String asignarDocumentacion(@PathVariable Long legajoId, 
	                                          @RequestParam Long documentacionId,
	                                          @RequestParam String estado,
	                                          @RequestParam(required = false) String fechaAsignacion,
	                                          @RequestParam(required = false) String observaciones,
	                                          Model model) {
	            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(legajoId);
	            Optional<Documentacion> documentacionOp = DocumentacionService.Busquedaporid(documentacionId);
	            
	            if (legajoOp.isPresent() && documentacionOp.isPresent()) {
	                try {
	                    Documentacion documentacion = documentacionOp.get();
	                    Legajo legajo = legajoOp.get();
	                    
	                    // Verificar que la documentación no esté ya asignada
	                    if (documentacion.getLegajo() != null) {
	                        throw new RuntimeException("Esta documentación ya está asignada a otro legajo");
	                    }
	                    
	                    // Validar estado
	                    if (estado == null || estado.isEmpty()) {
	                        throw new RuntimeException("Debe seleccionar un estado para la documentación");
	                    }
	                    
	                    if (!estado.equals("Pendiente") && !estado.equals("Entregado")) {
	                        throw new RuntimeException("El estado debe ser 'Pendiente' o 'Entregado'");
	                    }
	                    
	                    // Asignar documentación al legajo y actualizar estado
	                    documentacion.setLegajo(legajo);
	                    documentacion.setEstado(estado);
	                    
	                    // Si se proporciona una fecha de asignación, actualizar fecha de ingreso
	                    if (fechaAsignacion != null && !fechaAsignacion.isEmpty()) {
	                        try {
	                            java.time.LocalDate fecha = java.time.LocalDate.parse(fechaAsignacion);
	                            documentacion.setFechaIngreso(fecha);
	                        } catch (Exception e) {
	                            // Si hay error al parsear la fecha, mantener la fecha actual
	                        }
	                    }
	                    
	                    DocumentacionService.Guardar(documentacion);
	                    
	                    // Registrar en historial
	                    Historial historial = new Historial();
	                    historial.setLegajo(legajo);
	                    historial.setTipoEvento("DOCUMENTACION_ASIGNADA");
	                    historial.setFechaHora(LocalDateTime.now());
	                    String descripcionHistorial = "Documentación '" + documentacion.getTipoDocumento() + 
	                                                 "' (Nº " + documentacion.getNumero() + 
	                                                 ") asignada al legajo. Estado: " + estado;
	                    if (fechaAsignacion != null && !fechaAsignacion.isEmpty()) {
	                        descripcionHistorial += ". Fecha de asignación: " + fechaAsignacion;
	                    }
	                    historial.setDescripcion(descripcionHistorial);
	                    HistorialService.Guardar(historial);
	                    
	                    return "redirect:/detallelegajo/" + legajoId + "?mensaje=Documentación asignada correctamente con estado: " + estado;
	                } catch (RuntimeException e) {
	                    model.addAttribute("error", e.getMessage());
	                    model.addAttribute("legajo", legajoOp.get());
	                    // Recargar documentaciones disponibles
	                    List<Documentacion> todasDocumentaciones = DocumentacionService.getDocumentacion();
	                    List<Documentacion> documentacionesDisponibles = todasDocumentaciones.stream()
	                        .filter(d -> d.getLegajo() == null)
	                        .collect(java.util.stream.Collectors.toList());
	                    model.addAttribute("documentacionesDisponibles", documentacionesDisponibles);
	                    return "Vistas/asignar_documentacion";
	                }
	            }
	            return "redirect:/mostrarlista";
	        }
	        
	        @GetMapping("/cambiarEstadoDocumentacion/{legajoId}/{docId}/{nuevoEstado}")
	        public String cambiarEstadoDocumentacion(@PathVariable Long legajoId,
	                                                 @PathVariable Long docId,
	                                                 @PathVariable String nuevoEstado) {
	            Optional<Documentacion> documentacionOp = DocumentacionService.Busquedaporid(docId);
	            
	            if (documentacionOp.isPresent()) {
	                Documentacion documentacion = documentacionOp.get();
	                
	               
	                if (nuevoEstado.equals("Pendiente") || nuevoEstado.equals("Entregado")) {
	                    
	                    if (documentacion.getLegajo() != null && documentacion.getLegajo().getId().equals(legajoId)) {
	                        String estadoAnterior = documentacion.getEstado();
	                        documentacion.setEstado(nuevoEstado);
	                        DocumentacionService.Guardar(documentacion);
	                        
	                        
	                        Optional<Legajo> legajoOp = LegajoService.Busquedaporid(legajoId);
	                        if (legajoOp.isPresent()) {
	                            Historial historial = new Historial();
	                            historial.setLegajo(legajoOp.get());
	                            historial.setTipoEvento("DOCUMENTACION_ESTADO_CAMBIADO");
	                            historial.setFechaHora(LocalDateTime.now());
	                            String descripcionHistorial = "Estado de documentación '" + documentacion.getTipoDocumento() + 
	                                                         "' (Nº " + documentacion.getNumero() + ") cambiado";
	                            if (estadoAnterior != null && !estadoAnterior.isEmpty()) {
	                                descripcionHistorial += " de '" + estadoAnterior + "'";
	                            }
	                            descripcionHistorial += " a '" + nuevoEstado + "'";
	                            historial.setDescripcion(descripcionHistorial);
	                            HistorialService.Guardar(historial);
	                        }
	                        
	                        return "redirect:/detallelegajo/" + legajoId + "?mensaje=Estado de documentación actualizado a: " + nuevoEstado;
	                    } else {
	                        return "redirect:/detallelegajo/" + legajoId + "?error=La documentación no pertenece a este legajo";
	                    }
	                } else {
	                    return "redirect:/detallelegajo/" + legajoId + "?error=Estado inválido";
	                }
	            }
	            
	            return "redirect:/detallelegajo/" + legajoId + "?error=Documentación no encontrada";
	        }
	        
	        @GetMapping("/eliminarlegajo/{id}")
	        public String eliminarLegajo(@PathVariable Long id) {
	            Optional<Legajo> legajoOp = LegajoService.Busquedaporid(id);
	            if (legajoOp.isPresent()) {
	                LegajoService.Eliminar(legajoOp.get());
	            }
	            return "redirect:/mostrarlista";
	        }

	       
	
}
