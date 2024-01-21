package PROY.BAKENDAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import PROY.BAKENDAPI.domain.MovimientosBroker;

public interface MovimientosBrokerRepository extends JpaRepository<MovimientosBroker, Long> {

    List<MovimientosBroker> findByusuarioId(Long idUsuario);

    List<MovimientosBroker> findByUsuarioId(Long id);

    List<MovimientosBroker> findByBrokerId(Long idbroker);

}
