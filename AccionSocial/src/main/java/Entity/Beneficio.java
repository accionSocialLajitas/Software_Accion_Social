package Entity;

import jakarta.persistence.*;

@Entity
public class Beneficio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int cantidadDisponible;
    private Double monto;
    private String descripcion;
    private String estado;
    private String tipoBeneficio;
    
	public Beneficio() {
		super();
	}

	public Beneficio(Long id, String nombre, int cantidadDisponible, Double monto, String descripcion, String estado,
			String tipoBeneficio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidadDisponible = cantidadDisponible;
		this.monto = monto;
		this.descripcion = descripcion;
		this.estado = estado;
		this.tipoBeneficio = tipoBeneficio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadDisponible() {
		return cantidadDisponible;
	}

	public void setCantidadDisponible(int cantidadDisponible) {
		this.cantidadDisponible = cantidadDisponible;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoBeneficio() {
		return tipoBeneficio;
	}

	public void setTipoBeneficio(String tipoBeneficio) {
		this.tipoBeneficio = tipoBeneficio;
	}
    
	
    
}
