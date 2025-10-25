package Entity;

import jakarta.persistence.*;


@Entity
public class Direccion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_direccion;
    private String localidad;
    private String calle;
    private String numero_casa;
    
    public Direccion() {
        super();
    }

    public Direccion(Long id_direccion, String localidad, String calle, String numero_casa) {
        super();
        this.id_direccion = id_direccion;
        this.localidad = localidad;
        this.calle = calle;
        this.numero_casa = numero_casa;
    }

    public Long getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Long id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero_casa() {
        return numero_casa;
    }

    public void setNumero_casa(String numero_casa) {
        this.numero_casa = numero_casa;
    }
}
