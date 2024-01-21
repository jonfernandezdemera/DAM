package PROY.BAKENDAPI.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;

import PROY.BAKENDAPI.domain.Usuario;
import PROY.BAKENDAPI.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	// LISTADO
	@GetMapping({ "/", "" })
	public List<Usuario> getAllUsers() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios;
	}

	// CONSULTAS
	@GetMapping("/id/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable("id") Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (usuario == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Usuario> getByEmail(@PathVariable("email") String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	@GetMapping("/login")
    public ResponseEntity<Usuario> autenticarUsuario(@RequestParam("email") String email,@RequestParam("password") String password) {
        Usuario usuario = usuarioRepository.findByEmail(email);

		if (usuario != null) {
			String encodedPassword = Base64.getEncoder().encodeToString(usuario.getPassword());
			if (password.equals(encodedPassword)) {
				return new ResponseEntity<Usuario>(usuario, HttpStatus.OK); //Devuelve el usuario si es autenticado correctamente
				
			} else {
				return new ResponseEntity<Usuario>(new Usuario(),HttpStatus.UNAUTHORIZED); //En caso de estar mal la contraseña, devuelve UNAUTHORIZED y un usuario en null
			}
		} else {
			return new ResponseEntity<Usuario>(new Usuario(),HttpStatus.NOT_FOUND); // Si no encuentra el email devuelve NOT_FOUND y un usuario en null
		}
    }

	// CREACION - ACTUALIZACION
	@PostMapping({ "/", "" })
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario createOrUpdate(@RequestBody Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	// ACTUALIZACION
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario update(@RequestBody Usuario usuario, @PathVariable("id") Long id) {
		Usuario tempUsuario = usuarioRepository.findById(id).orElse(null);

		tempUsuario.setNombre(usuario.getNombre());
		tempUsuario.setApellidos(usuario.getApellidos());
		tempUsuario.setEmail(usuario.getEmail());
		tempUsuario.setEdad(usuario.getEdad());
		tempUsuario.setPassword(usuario.getPassword());

		return usuarioRepository.save(tempUsuario);
	}

	// ELIMINACION
	@DeleteMapping("/id/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		usuarioRepository.deleteById(id);
	}

	@DeleteMapping("/email/{email}")
	@Transactional
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteByEmail(@PathVariable("email") String email) {
		usuarioRepository.deleteByEmail(email);
	}

}
