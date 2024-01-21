package PROY.BAKENDAPI.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seguimiento")

public class Seguimiento implements Serializable{
   
    /**
    * 
    */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SeguimientoId seguimientoId;

    @ManyToOne
    @MapsId("simbolo")
    @JoinColumn(name = "simbolo")
    private Empresa empresa;

    @ManyToOne
    @MapsId("idusuario")
    @JoinColumn(name = "idusuario")
    private Usuario usuario;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Embeddable
    public static class SeguimientoId implements Serializable {

        private String simbolo;
        private Long idusuario;
    }

    @Override
    public String toString() {
        return "Seguimiento [Simbolo=" + empresa + ", usuario=" + usuario + "]";
    }
}
