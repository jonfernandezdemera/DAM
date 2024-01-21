package PROY.BAKENDAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import PROY.BAKENDAPI.domain.Broker;

public interface BrokerRepository extends JpaRepository<Broker, Long> {

    Broker findByNombre(String nombre);

}
