
package PROY.BAKENDAPI.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacciones")
public class Transacciones implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idbroker")
    private Broker broker;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "simbolo")
    private Empresa simbolo;

    @Column(name = "operacion")
    private Integer operacion;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "numacciones")
    private Integer numAcciones;

    @Column(name = "precioaccion", precision = 10, scale = 2)
    private BigDecimal precioAccion;

    // Constructor
    public Transacciones(Broker broker, Usuario usuario, Empresa simbolo,
            Integer operacion, Date fecha, Integer numAcciones, BigDecimal precioAccion) {
        this.broker = broker;
        this.usuario = usuario;
        this.simbolo = simbolo;
        this.operacion = operacion;
        this.fecha = fecha;
        this.numAcciones = numAcciones;
        this.precioAccion = precioAccion;
    }

    @Override
    public String toString() {
        return "Transacciones [id=" + id +
                ", idBroker=" + broker +
                ", idUsuario=" + usuario +
                ", simbolo=" + simbolo +
                ", operacion=" + operacion +
                ", fecha=" + fecha +
                ", numAcciones=" + numAcciones +
                ", precioAccion=" + precioAccion +
                "]";
    }
}
