package PROY.BAKENDAPI.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "empresa")
public class Empresa implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "simbolo")
    private String simbolo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "sector")
    private String sector;

    @Column(name = "cotizacion")
    private Double cotizacion;

    @Column(name = "divisa")
    private String divisa;

    @Column(name = "PER")
    private Double per;

    @Column(name = "BPA")
    private Double bpa;

    @Column(name = "activo")
    private Boolean activo;

    @Override
    public String toString() {
        return "Empresa [simbolo=" + simbolo + ", nombre=" + nombre + ", sector=" + sector + ", cotizacion="
                + cotizacion + ", divisa=" + divisa + ", PER=" + per + ", BPA=" + bpa + ", activo=" + activo + "]";
    }
}