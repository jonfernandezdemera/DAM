package PROY.BAKENDAPI.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import PROY.BAKENDAPI.domain.Empresa;
import PROY.BAKENDAPI.domain.Seguimiento;
import PROY.BAKENDAPI.domain.Usuario;
import PROY.BAKENDAPI.repository.EmpresaRepository;
import PROY.BAKENDAPI.repository.SeguimientoRepository;
import PROY.BAKENDAPI.repository.UsuarioRepository;

@RestController
@RequestMapping("api/seguimiento")
public class SeguimientoController {
       
    @Autowired
    private final SeguimientoRepository seguimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    
    public SeguimientoController(SeguimientoRepository seguimientoRepository, UsuarioRepository usuarioRepository, EmpresaRepository empresaRepository) {
        this.seguimientoRepository = seguimientoRepository;
        this.usuarioRepository = usuarioRepository;
        this.empresaRepository = empresaRepository;
    }

    // LISTADO

    // CONSULTAS
    // Consulta empresas en seguimiento por Usuario 
    @GetMapping("/idusuario/{idUsuario}")   
    public ResponseEntity<List<Empresa>> getSeguimientoByUsuario(@PathVariable Long idUsuario) {
        List<Seguimiento> seguimientos = seguimientoRepository.findByUsuarioId(idUsuario);
        List<Empresa> empresas = seguimientos.stream()
                .map(Seguimiento::getEmpresa)
                .collect(Collectors.toList());

        if (!empresas.isEmpty()) {
            return new ResponseEntity<>(empresas, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // CREACIÓN 
    @PostMapping({ "/", "" })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createSeguimiento(@RequestBody Seguimiento seguimiento) {
        // Obtener el usuario y empresa por sus IDs
        Usuario usuario = usuarioRepository.findById(seguimiento.getSeguimientoId().getIdusuario()).orElse(null);
        Empresa empresa = empresaRepository.findById(seguimiento.getSeguimientoId().getSimbolo()).orElse(null);

        // Imprimir el valor del seguimiento en consola
        System.out.println("Valor del objeto seguimiento: " + seguimiento
                            + "Usuario: " + usuario
                            + "Empresa: " + empresa);

        // Verificamos que existe el usuario o empresa
        if (usuario != null && empresa != null) {
            seguimiento.setUsuario(usuario);
            seguimiento.setEmpresa(empresa);

            Seguimiento nuevoSeguimiento = seguimientoRepository.save(seguimiento);
            return ResponseEntity.ok("Seguimiento creado con ID: " + nuevoSeguimiento.getSeguimientoId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o empresa no encontrados");          
        }
    }

    // ELIMINACIÓN
    // Eliminación por idUsuario y simbolo
    @DeleteMapping("/simbolo/{simbolo}/idusuario/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeguimientoById(@PathVariable("simbolo") String simbolo, @PathVariable("idUsuario") Long idUsuario) {
        Seguimiento.SeguimientoId seguimientoId = new Seguimiento.SeguimientoId(simbolo, idUsuario);
        seguimientoRepository.deleteById(seguimientoId);
    }
}
