package PROY.BAKENDAPI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import PROY.BAKENDAPI.domain.Empresa;

public interface EmpresaRepository extends JpaRepository <Empresa, String> {

    Empresa findBySimbolo(String simbolo);

    Empresa findByNombre(String nombre);

    List<Empresa> findBySector(String sector);

    void deleteBySimbolo(String simbolo);

    void deleteByNombre(String nombre);

}
