package PROY.BAKENDAPI.domain;

import java.io.Serializable;
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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "movimientos_broker")
public class MovimientosBroker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idbroker")
	private Broker broker;

	@Column(name = "concepto")
	private String concepto;

	@Column(name = "operacion")
	private int operacion;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "importe")
	private Double importe;

	@Column(name = "comision")
	private Double comision;

	@Column(name = "idoperacion")
	private Long idoperacion;

	public MovimientosBroker(Long idbroker, int operacion, String concepto, Date fecha, Double importe, Double comision,
			Long idoperacion) {
		this.id = idbroker;
		this.operacion = operacion;
		this.concepto = concepto;
		this.fecha = fecha;
		this.importe = importe;
		this.comision = comision;
		this.idoperacion = idoperacion;
	}

	@Override
	public String toString() {
		return "MovimientosBroker [id=" + id + ", idbroker=" + id + ", operacion="
				+ operacion + ", fecha=" + fecha + ", conecpto=" + concepto + ", importe=" + importe + ", comision="
				+ comision + ", idoperacion=" + idoperacion + "]";
	}

}
