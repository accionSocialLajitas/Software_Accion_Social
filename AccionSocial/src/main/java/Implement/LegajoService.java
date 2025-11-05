package Implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import Entity.Legajo;
import Entity.Historial;
import Entity.Direccion;
import Repository.LegajoRepository;
import Service.InterLegajo;
import Service.InterHistorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegajoService implements InterLegajo {
    
    @Autowired
    private LegajoRepository LegajoRepository;
    
    @Autowired
    private InterHistorial historialService;


@Override
public Legajo Guardar(Legajo legajo) {
	boolean esNuevo = legajo.getId() == null;
	Legajo legajoAnterior = null;
	
	// Si es una edición, obtener el legajo anterior ANTES de guardar para comparar cambios
	if (!esNuevo && legajo.getId() != null) {
	    // Usar findByIdAndActivoTrue para obtener el legajo activo
	    Optional<Legajo> legajoAnteriorOp = LegajoRepository.findByIdAndActivoTrue(legajo.getId());
	    if (legajoAnteriorOp.isPresent()) {
	        legajoAnterior = legajoAnteriorOp.get();
	        // Forzar la carga de la dirección si existe
	        if (legajoAnterior.getDireccion() != null) {
	            legajoAnterior.getDireccion().getCalle(); // Trigger lazy loading
	        }
	    }
	}
	
	Legajo legajoGuardado = LegajoRepository.save(legajo);
	
	// Si es un nuevo legajo, registrar en el historial
	if (esNuevo && legajoGuardado.getId() != null) {
	    Historial historial = new Historial();
	    historial.setLegajo(legajoGuardado);
	    historial.setTipoEvento("LEGAJO_CREADO");
	    historial.setFechaHora(LocalDateTime.now());
	    historial.setDescripcion("Legajo creado para: " + legajoGuardado.getNombre() + " " + 
	                            legajoGuardado.getApellido() + " (DNI: " + legajoGuardado.getDni() + 
	                            "). Estado: " + legajoGuardado.getEstado());
	    historialService.Guardar(historial);
	}
	// Si es una edición, registrar los cambios en el historial
	else if (!esNuevo) {
	    // Si no se pudo obtener el legajo anterior, registrar edición genérica
	    if (legajoAnterior == null) {
	        Historial historial = new Historial();
	        historial.setLegajo(legajoGuardado);
	        historial.setTipoEvento("LEGAJO_EDITADO");
	        historial.setFechaHora(LocalDateTime.now());
	        historial.setDescripcion("Legajo editado: " + legajoGuardado.getNombre() + " " + 
	                                legajoGuardado.getApellido() + " (DNI: " + legajoGuardado.getDni() + ")");
	        historialService.Guardar(historial);
	        return legajoGuardado;
	    }
	    StringBuilder cambios = new StringBuilder();
	    StringBuilder camposEditados = new StringBuilder();
	    boolean hayCambios = false;
	    int contadorCampos = 0;
	    
	    if (!comparaValor(legajoAnterior.getNombre(), legajoGuardado.getNombre())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Nombre");
	        cambios.append("Nombre: ").append(legajoAnterior.getNombre() != null ? legajoAnterior.getNombre() : "(vacío)").append(" → ").append(legajoGuardado.getNombre() != null ? legajoGuardado.getNombre() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getApellido(), legajoGuardado.getApellido())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Apellido");
	        cambios.append("Apellido: ").append(legajoAnterior.getApellido() != null ? legajoAnterior.getApellido() : "(vacío)").append(" → ").append(legajoGuardado.getApellido() != null ? legajoGuardado.getApellido() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getDni(), legajoGuardado.getDni())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("DNI");
	        cambios.append("DNI: ").append(legajoAnterior.getDni() != null ? legajoAnterior.getDni() : "(vacío)").append(" → ").append(legajoGuardado.getDni() != null ? legajoGuardado.getDni() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getTelefono(), legajoGuardado.getTelefono())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Teléfono");
	        cambios.append("Teléfono: ").append(legajoAnterior.getTelefono() != null ? legajoAnterior.getTelefono() : "(vacío)").append(" → ").append(legajoGuardado.getTelefono() != null ? legajoGuardado.getTelefono() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getEmail(), legajoGuardado.getEmail())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Email");
	        cambios.append("Email: ").append(legajoAnterior.getEmail() != null ? legajoAnterior.getEmail() : "(vacío)").append(" → ").append(legajoGuardado.getEmail() != null ? legajoGuardado.getEmail() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getEstado(), legajoGuardado.getEstado())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Estado");
	        cambios.append("Estado: ").append(legajoAnterior.getEstado() != null ? legajoAnterior.getEstado() : "(vacío)").append(" → ").append(legajoGuardado.getEstado() != null ? legajoGuardado.getEstado() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (legajoAnterior.getCant_hijos() != legajoGuardado.getCant_hijos()) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Cantidad de hijos");
	        cambios.append("Cantidad de hijos: ").append(legajoAnterior.getCant_hijos()).append(" → ").append(legajoGuardado.getCant_hijos()).append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getOrigen(), legajoGuardado.getOrigen())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Origen");
	        cambios.append("Origen: ").append(legajoAnterior.getOrigen() != null ? legajoAnterior.getOrigen() : "(vacío)").append(" → ").append(legajoGuardado.getOrigen() != null ? legajoGuardado.getOrigen() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getCategoria(), legajoGuardado.getCategoria())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Categoría");
	        cambios.append("Categoría: ").append(legajoAnterior.getCategoria() != null ? legajoAnterior.getCategoria() : "(vacío)").append(" → ").append(legajoGuardado.getCategoria() != null ? legajoGuardado.getCategoria() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getEstado_civil(), legajoGuardado.getEstado_civil())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Estado Civil");
	        cambios.append("Estado Civil: ").append(legajoAnterior.getEstado_civil() != null ? legajoAnterior.getEstado_civil() : "(vacío)").append(" → ").append(legajoGuardado.getEstado_civil() != null ? legajoGuardado.getEstado_civil() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getDiscapacidad(), legajoGuardado.getDiscapacidad())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Discapacidad");
	        cambios.append("Discapacidad: ").append(legajoAnterior.getDiscapacidad() != null ? legajoAnterior.getDiscapacidad() : "(vacío)").append(" → ").append(legajoGuardado.getDiscapacidad() != null ? legajoGuardado.getDiscapacidad() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    if (!comparaValor(legajoAnterior.getAdulto_mayor(), legajoGuardado.getAdulto_mayor())) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Adulto Mayor");
	        cambios.append("Adulto Mayor: ").append(legajoAnterior.getAdulto_mayor() != null ? legajoAnterior.getAdulto_mayor() : "(vacío)").append(" → ").append(legajoGuardado.getAdulto_mayor() != null ? legajoGuardado.getAdulto_mayor() : "(vacío)").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    
	    
	    if (legajoAnterior.getDireccion() != null && legajoGuardado.getDireccion() != null) {
	        Direccion dirAnterior = legajoAnterior.getDireccion();
	        Direccion dirNueva = legajoGuardado.getDireccion();
	        
	        if (!comparaValor(dirAnterior.getCalle(), dirNueva.getCalle())) {
	            if (contadorCampos > 0) camposEditados.append(", ");
	            camposEditados.append("Dirección - Calle");
	            cambios.append("Dirección - Calle: ").append(dirAnterior.getCalle() != null ? dirAnterior.getCalle() : "(vacío)").append(" → ").append(dirNueva.getCalle() != null ? dirNueva.getCalle() : "(vacío)").append(". ");
	            hayCambios = true;
	            contadorCampos++;
	        }
	        if (!comparaValor(dirAnterior.getLocalidad(), dirNueva.getLocalidad())) {
	            if (contadorCampos > 0) camposEditados.append(", ");
	            camposEditados.append("Dirección - Localidad");
	            cambios.append("Dirección - Localidad: ").append(dirAnterior.getLocalidad() != null ? dirAnterior.getLocalidad() : "(vacío)").append(" → ").append(dirNueva.getLocalidad() != null ? dirNueva.getLocalidad() : "(vacío)").append(". ");
	            hayCambios = true;
	            contadorCampos++;
	        }
	        if (!comparaValor(dirAnterior.getNumero_casa(), dirNueva.getNumero_casa())) {
	            if (contadorCampos > 0) camposEditados.append(", ");
	            camposEditados.append("Dirección - Número");
	            cambios.append("Dirección - Número: ").append(dirAnterior.getNumero_casa() != null ? dirAnterior.getNumero_casa() : "(vacío)").append(" → ").append(dirNueva.getNumero_casa() != null ? dirNueva.getNumero_casa() : "(vacío)").append(". ");
	            hayCambios = true;
	            contadorCampos++;
	        }
	    } else if (legajoAnterior.getDireccion() == null && legajoGuardado.getDireccion() != null) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Dirección (agregada)");
	        cambios.append("Dirección agregada: ").append(legajoGuardado.getDireccion().getCalle() != null ? legajoGuardado.getDireccion().getCalle() : "").append(" ").append(legajoGuardado.getDireccion().getNumero_casa() != null ? legajoGuardado.getDireccion().getNumero_casa() : "").append(", ").append(legajoGuardado.getDireccion().getLocalidad() != null ? legajoGuardado.getDireccion().getLocalidad() : "").append(". ");
	        hayCambios = true;
	        contadorCampos++;
	    } else if (legajoAnterior.getDireccion() != null && legajoGuardado.getDireccion() == null) {
	        if (contadorCampos > 0) camposEditados.append(", ");
	        camposEditados.append("Dirección (eliminada)");
	        cambios.append("Dirección eliminada. ");
	        hayCambios = true;
	        contadorCampos++;
	    }
	    
	    Historial historial = new Historial();
	    historial.setLegajo(legajoGuardado);
	    historial.setTipoEvento("LEGAJO_EDITADO");
	    historial.setFechaHora(LocalDateTime.now());
	    
	    String descripcion;
	    if (hayCambios) {
	        descripcion = "Legajo editado: " + legajoGuardado.getNombre() + " " + legajoGuardado.getApellido() + " (DNI: " + legajoGuardado.getDni() + "). Campos editados: [" + camposEditados.toString() + "]. Detalles: " + cambios.toString();
	    } else {
	        descripcion = "Legajo editado: " + legajoGuardado.getNombre() + " " + legajoGuardado.getApellido() + " (DNI: " + legajoGuardado.getDni() + ")";
	    }
	    historial.setDescripcion(descripcion);
	    historialService.Guardar(historial);
	}
	
	return legajoGuardado;
}


private boolean comparaValor(String valor1, String valor2) {
    if (valor1 == null && valor2 == null) return true;
    if (valor1 == null || valor2 == null) return false;
    return valor1.equals(valor2);
}


@Override
public Optional<Legajo> Busquedaporid(Long id) {
    return LegajoRepository.findByIdAndActivoTrue(id);
}

@Override
public Legajo buscarLegajoPorDni(String dni) {
    return LegajoRepository.findByDniAndActivoTrue(dni);
}

@Override
public boolean existeDniDuplicado(String dni, Long id) {
    Legajo legajoExistente = LegajoRepository.findByDniAndActivoTrue(dni);
    if (legajoExistente == null) {
        return false;
    }
    return id == null || !legajoExistente.getId().equals(id);
}



@Override
public void Eliminar(Legajo legajo) {
    legajo.setActivo(false);
    LegajoRepository.save(legajo);
    Historial historial = new Historial();
    historial.setLegajo(legajo);
    historial.setTipoEvento("LEGAJO_ELIMINADO");
    historial.setFechaHora(LocalDateTime.now());
    historial.setDescripcion("Legajo eliminado (borrado lógico): " + legajo.getNombre() + " " + 
                            legajo.getApellido() + " (DNI: " + legajo.getDni() + ")");
    historialService.Guardar(historial);
}

@Override
public void Editar(Legajo legajo) {
    LegajoRepository.save(legajo);
}


@Override
public List<Legajo> getLegajo() {
	return LegajoRepository.findByActivoTrue();

}




}
