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
@Table(name = "dividendos")

public class Dividendos implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "simbolo")
    private String simbolo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idbroker")
    private Broker broker;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "precioaccion", precision = 10, scale = 2)
    private BigDecimal precioAccion;

    @Column(name = "retencion", precision = 5, scale = 2)
    private BigDecimal retencion;

    @Override
    public String toString() {
        return "TuClase{" +
                "id=" + id +
                ", simbolo='" + simbolo + '\'' +
                ", broker=" + broker +
                ", usuario=" + usuario +
                ", fecha=" + fecha +
                ", precioAccion=" + precioAccion +
                ", retencion=" + retencion +
                '}';
    }
}
