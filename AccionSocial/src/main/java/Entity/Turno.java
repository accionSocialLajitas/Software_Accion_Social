package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Entity
public class Turno {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@ManyToOne
    @JoinColumn(name = "id_legajo")
	private Legajo legajo;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_turno")
	private Direccion direccion;
	
    private LocalDate fecha;
    private LocalTime hora;
    private String observaciones;
    
	public Turno() {
		super();
	}

	public Turno(Long id, Legajo legajo, Direccion direccion, LocalDate fecha, LocalTime hora, String observaciones) {
		super();
		this.id = id;
		this.legajo = legajo;
		this.direccion = direccion;
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

	public Legajo getLegajo() {
		return legajo;
	}

	public void setLegajo(Legajo legajo) {
		this.legajo = legajo;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
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
