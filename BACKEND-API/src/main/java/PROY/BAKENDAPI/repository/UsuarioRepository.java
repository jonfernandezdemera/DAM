package PROY.BAKENDAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import PROY.BAKENDAPI.domain.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    Usuario findByEmail(String email);

    void deleteByEmail(String email);

}
