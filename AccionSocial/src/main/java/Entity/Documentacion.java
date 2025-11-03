package Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Documentacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipoDocumento;
    private String numero;
    private LocalDate fechaIngreso;
    private String estado; // "SOLICITADO" o "GUARDADO"
    
    @ManyToOne
    @JoinColumn(name = "id_legajo")
    private Legajo legajo;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;
    
	public Documentacion() {
		super();
	}

	public Documentacion(Long id, String tipoDocumento, String numero, LocalDate fechaIngreso, String estado, Legajo legajo, Ubicacion ubicacion) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
		this.numero = numero;
		this.fechaIngreso = fechaIngreso;
		this.estado = estado;
		this.legajo = legajo;
		this.ubicacion = ubicacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Legajo getLegajo() {
		return legajo;
	}

	public void setLegajo(Legajo legajo) {
		this.legajo = legajo;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
}
