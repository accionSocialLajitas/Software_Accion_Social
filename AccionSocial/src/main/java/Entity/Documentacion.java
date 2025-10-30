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
    
	public Documentacion() {
		super();
	}

	public Documentacion(Long id, String tipoDocumento, String numero, LocalDate fechaIngreso) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
		this.numero = numero;
		this.fechaIngreso = fechaIngreso;
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
}
