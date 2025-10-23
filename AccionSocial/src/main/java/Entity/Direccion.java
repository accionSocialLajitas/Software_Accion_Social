package Entity;

import jakarta.persistence.*;


@Entity
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String localidad;
    private String calle;
    private String casaN;

    public Direccion() {
    }

    public Direccion(String localidad, String calle, String casaN) {
        this.localidad = localidad;
        this.calle = calle;
        this.casaN = casaN;
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCasaN() {
        return casaN;
    }

    public void setCasaN(String casaN) {
        this.casaN = casaN;
    }

}
