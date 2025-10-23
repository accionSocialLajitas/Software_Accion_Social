package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private Long Id_Legajo;
    //private Long id_direccion;
    private LocalDate fecha;
    private LocalTime hora;
    private String observaciones;
    
	public Turno() {
		super();
	}

	public Turno(Long id, LocalDate fecha, LocalTime hora, String observaciones) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.observaciones = observaciones;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
    
    
    

}
