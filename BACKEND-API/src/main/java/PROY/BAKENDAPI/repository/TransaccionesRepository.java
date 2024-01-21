package PROY.BAKENDAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Date;

import PROY.BAKENDAPI.domain.Transacciones;

public interface TransaccionesRepository extends JpaRepository<Transacciones, Long> {

    List<Transacciones> findByUsuarioId(Long idUsuario);

    List<Transacciones> findByUsuarioIdAndBrokerId(Long idUsuario, Long idBroker);

    List<Transacciones> findByUsuarioIdAndOperacion(Long idUsuario, Integer operacion);

    List<Transacciones> findByUsuarioIdAndFecha(Long idUsuario, Date fecha);
}