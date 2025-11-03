package Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Historial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_legajo")
    private Legajo legajo;
    
    private String tipoEvento; 
    private LocalDateTime fechaHora;
    private String descripcion; 
    
    public Historial() {
        super();
    }

    public Historial(Long id, Legajo legajo, String tipoEvento, LocalDateTime fechaHora, String descripcion) {
        super();
        this.id = id;
        this.legajo = legajo;
        this.tipoEvento = tipoEvento;
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
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

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

