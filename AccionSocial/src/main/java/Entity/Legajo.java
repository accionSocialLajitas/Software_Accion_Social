package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;


/**
 * Entidad que representa un caso de acción social
 */
@Entity
public class Legajo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origen;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fecha_nac;
    private String telefono;
    private String categoria;
    private String email;
    private String estado_civil;
    private int cant_hijos;
    private String discapacidad;
    private String adulto_mayor;
    private String estado;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

	public Legajo() {
		super();
	}

	public Legajo(Long id, String origen, String nombre, String apellido, String dni, LocalDate fecha_nac,
			String telefono, String categoria, String email, String estado_civil, int cant_hijos, String discapacidad,
			String adulto_mayor, String estado, Direccion direccion) {
		super();
		this.id = id;
		this.origen = origen;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fecha_nac = fecha_nac;
		this.telefono = telefono;
		this.categoria = categoria;
		this.email = email;
		this.estado_civil = estado_civil;
		this.cant_hijos = cant_hijos;
		this.discapacidad = discapacidad;
		this.adulto_mayor = adulto_mayor;
		this.estado = estado;
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDate getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(LocalDate fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEstado_civil() {
		return estado_civil;
	}

	public void setEstado_civil(String estado_civil) {
		this.estado_civil = estado_civil;
	}

	public int getCant_hijos() {
		return cant_hijos;
	}

	public void setCant_hijos(int cant_hijos) {
		this.cant_hijos = cant_hijos;
	}

	public String getDiscapacidad() {
		return discapacidad;
	}

	public void setDiscapacidad(String discapacidad) {
		this.discapacidad = discapacidad;
	}

	public String getAdulto_mayor() {
		return adulto_mayor;
	}

	public void setAdulto_mayor(String adulto_mayor) {
		this.adulto_mayor = adulto_mayor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
    
    

}
