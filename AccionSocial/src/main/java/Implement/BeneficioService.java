package Implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import Entity.Beneficio;
import Entity.Historial;
import Entity.Legajo;
import Repository.BeneficioRepository;
import Service.InterBeneficio;
import Service.InterHistorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeneficioService implements InterBeneficio {
    
    @Autowired
    private BeneficioRepository beneficioRepository;
    
    @Autowired
    private InterHistorial historialService;

    @Override
    public void Guardar(Beneficio beneficio) {
        beneficioRepository.save(beneficio);
    }

    @Override
    public Optional<Beneficio> Busquedaporid(Long id) {
        return beneficioRepository.findById(id);
    }

    @Override
    public Beneficio buscarBeneficioPorNombre(String nombre) {
        return beneficioRepository.findByNombre(nombre);
    }

    @Override
    public void Eliminar(Beneficio beneficio) {
        beneficioRepository.delete(beneficio);
    }

    @Override
    public void Editar(Beneficio beneficio) {
        beneficioRepository.save(beneficio);
    }

    @Override
    public List<Beneficio> getBeneficio() {
        return beneficioRepository.findAll();
    }
    
    public void asignarBeneficioALegajo(Beneficio beneficio, Legajo legajo, Integer cantidad, Double montoPersonalizado) {
        Optional<Beneficio> beneficioOp = beneficioRepository.findById(beneficio.getId());
        if (!beneficioOp.isPresent()) {
            throw new RuntimeException("Beneficio no encontrado");
        }
        
        Beneficio beneficioOriginal = beneficioOp.get();
        
        String tipoBeneficio = beneficioOriginal.getTipoBeneficio();
        boolean esBeneficioMonto = tipoBeneficio != null && ("fondos".equals(tipoBeneficio) || "monto".equals(tipoBeneficio));
        
        if (esBeneficioMonto) {
            if (montoPersonalizado == null || montoPersonalizado <= 0) {
                throw new RuntimeException("Debe especificar un monto mayor a 0 para este beneficio");
            }
            
            if (beneficioOriginal.getMonto() == null || beneficioOriginal.getMonto() <= 0) {
                throw new RuntimeException("El beneficio no tiene monto disponible");
            }
            
            if (montoPersonalizado > beneficioOriginal.getMonto()) {
                throw new RuntimeException("El monto solicitado (" + montoPersonalizado + ") excede el monto disponible (" + 
                                        beneficioOriginal.getMonto() + ")");
            }
            
            cantidad = 1;
        } else {
            if (cantidad == null || cantidad <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a 0");
            }
            
            if (cantidad > beneficioOriginal.getCantidadDisponible()) {
                throw new RuntimeException("La cantidad solicitada (" + cantidad + ") excede la cantidad disponible (" + 
                                          beneficioOriginal.getCantidadDisponible() + ")");
            }
            
            if (beneficioOriginal.getCantidadDisponible() <= 0) {
                throw new RuntimeException("No hay cantidad disponible del beneficio");
            }
        }
        
        if (beneficioOriginal.getEstado() != null && beneficioOriginal.getEstado().equals("Inactivo")) {
            throw new RuntimeException("El beneficio está inactivo");
        }
        
     
        Double montoOriginal = beneficioOriginal.getMonto();
        int cantidadOriginal = beneficioOriginal.getCantidadDisponible();

        Double montoAsignado = montoPersonalizado;
        
        if (!esBeneficioMonto && montoAsignado == null) {
            montoAsignado = 0.0;
        }
        
        if (esBeneficioMonto) {
            if (montoAsignado == null || montoAsignado <= 0) {
                throw new RuntimeException("Debe especificar un monto mayor a 0 para descontar");
            }
            double nuevoMonto = montoOriginal - montoAsignado;
            beneficioOriginal.setMonto(nuevoMonto);
            if (nuevoMonto <= 0) {
                beneficioOriginal.setEstado("Inactivo");
                beneficioOriginal.setMonto(0.0);
            }
        } else {
            Double montoUnitario = null;
            if (montoOriginal != null && montoOriginal > 0 && cantidadOriginal > 0) {
                montoUnitario = montoOriginal / cantidadOriginal;
                if (montoAsignado == null) {
                    montoAsignado = montoUnitario * cantidad;
                }
            }
            
            
            int nuevaCantidad = cantidadOriginal - cantidad;
            beneficioOriginal.setCantidadDisponible(nuevaCantidad);
            if (montoOriginal != null && montoOriginal > 0 && montoUnitario != null) {
                double nuevoMonto = montoUnitario * nuevaCantidad;
                beneficioOriginal.setMonto(nuevoMonto);
            }
            if (nuevaCantidad == 0) {
                beneficioOriginal.setEstado("Inactivo");
            }
        }

        beneficioOriginal.setLegajo(null); 
        beneficioRepository.save(beneficioOriginal);
        
        Beneficio beneficioExistente = beneficioRepository.findByLegajoAndNombre(legajo, beneficioOriginal.getNombre());
        
        if (beneficioExistente != null) {
        
            if (esBeneficioMonto) {
              
                Double montoAnterior = beneficioExistente.getMonto();
                Double montoTotal = (montoAnterior != null ? montoAnterior : 0.0) + (montoAsignado != null ? montoAsignado : 0.0);
                beneficioExistente.setMonto(montoTotal);
                beneficioExistente.setCantidadDisponible(1);
            } else {
                int cantidadAnterior = beneficioExistente.getCantidadDisponible();
                Double montoAnterior = beneficioExistente.getMonto();
                
                beneficioExistente.setCantidadDisponible(cantidadAnterior + cantidad);
                
                if (montoAsignado != null) {
                    Double montoTotal = (montoAnterior != null ? montoAnterior : 0.0) + montoAsignado;
                    beneficioExistente.setMonto(montoTotal);
                } else {
                   
                    beneficioExistente.setMonto(montoAnterior != null ? montoAnterior : 0.0);
                }
            }
            beneficioRepository.save(beneficioExistente);
        } else {
            Beneficio beneficioParaLegajo = new Beneficio();
            beneficioParaLegajo.setNombre(beneficioOriginal.getNombre());
            
            if (esBeneficioMonto) {
                beneficioParaLegajo.setCantidadDisponible(1);
                
                beneficioParaLegajo.setMonto(montoAsignado);
            } else {
                beneficioParaLegajo.setCantidadDisponible(cantidad);
              
                beneficioParaLegajo.setMonto(montoAsignado != null ? montoAsignado : 0.0);
            }
            
            beneficioParaLegajo.setDescripcion(beneficioOriginal.getDescripcion());
            beneficioParaLegajo.setEstado("Asignado");
            beneficioParaLegajo.setTipoBeneficio(beneficioOriginal.getTipoBeneficio());
            beneficioParaLegajo.setLegajo(legajo);
            beneficioRepository.save(beneficioParaLegajo);
        }
        
       
        Historial historial = new Historial();
        historial.setLegajo(legajo);
        historial.setTipoEvento("BENEFICIO_OTORGADO");
        historial.setFechaHora(LocalDateTime.now());
        String descripcionHistorial = "Beneficio '" + beneficioOriginal.getNombre() + "' otorgado. ";
        
        if (esBeneficioMonto) {
            descripcionHistorial += "Monto: $" + (montoAsignado != null ? String.format("%.2f", montoAsignado) : "N/A") + ". ";
            descripcionHistorial += "Tipo: " + beneficioOriginal.getTipoBeneficio() + ". ";
            descripcionHistorial += "Monto restante: $" + String.format("%.2f", beneficioOriginal.getMonto() != null ? beneficioOriginal.getMonto() : 0.0);
        } else {
            descripcionHistorial += "Cantidad: " + cantidad + ". ";
            descripcionHistorial += "Monto: $" + (montoAsignado != null ? String.format("%.2f", montoAsignado) : "N/A") + ". ";
            descripcionHistorial += "Tipo: " + beneficioOriginal.getTipoBeneficio() + ". ";
            descripcionHistorial += "Cantidad restante: " + beneficioOriginal.getCantidadDisponible();
        }
        
        historial.setDescripcion(descripcionHistorial);
        historialService.Guardar(historial);
    }
}
