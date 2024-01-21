package PROY.BAKENDAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Date;

import PROY.BAKENDAPI.domain.Dividendos;

public interface DividendosRepository extends JpaRepository<Dividendos, Integer> {

     List<Dividendos> findByUsuarioId(Long usuarioId); // Para todos los de un usuario
     List<Dividendos> findBySimboloAndUsuarioId(String simbolo, Long usuarioId);  // Para buscar por empresa y usuario
     List<Dividendos> findByUsuarioIdAndFechaAndSimbolo(Long usuarioId, Date fecha, String simbolo); // busca por fecha, usuario y simbolo
     List<Dividendos> findByUsuarioIdAndFechaBetweenAndSimbolo(Long usuarioId, Date fechaInicio, Date fechaFin, String simbolo);// busca entre dos fechas, usuario y simbolo
}
