package PROY.BAKENDAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import PROY.BAKENDAPI.domain.Seguimiento;
import PROY.BAKENDAPI.domain.Seguimiento.SeguimientoId;

public interface SeguimientoRepository extends JpaRepository <Seguimiento, SeguimientoId>{
 
    List<Seguimiento> findByUsuarioId(Long id);
    void deleteById(SeguimientoId seguimientoId);
}
