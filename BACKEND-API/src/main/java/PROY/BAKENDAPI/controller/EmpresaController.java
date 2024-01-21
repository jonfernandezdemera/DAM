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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import PROY.BAKENDAPI.domain.Empresa;
import PROY.BAKENDAPI.repository.EmpresaRepository;

@RestController
@RequestMapping("api/empresas")
public class EmpresaController {

	@Autowired
	EmpresaRepository empresaRepository;

	// LISTADO
	@GetMapping({ "/", "" })
	public List<Empresa> getAllEnterprises() {
		List<Empresa> empresas = empresaRepository.findAll();
		return empresas;
	}

	// CONSULTAS
	@GetMapping("/simbolo/{simbolo}")
	public ResponseEntity<Empresa> getBySymbol(@PathVariable("simbolo") String simbolo) {
		Empresa empresa = empresaRepository.findBySimbolo(simbolo);
		if (empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}

	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<Empresa> getByName(@PathVariable("nombre") String nombre) {
		Empresa empresa = empresaRepository.findByNombre(nombre);
		if (empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}

	@GetMapping("/sector/{sector}")
	public ResponseEntity<List<Empresa>> getBySector(@PathVariable("sector") String sector) {
		List<Empresa> empresas = empresaRepository.findBySector(sector);
		if (empresas == null) {
			return new ResponseEntity<List<Empresa>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Empresa>>(empresas, HttpStatus.OK);
	}

	// CREACION - ACTUALIZACION
	@PostMapping({ "/", "" })
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa createOrUpdate(@RequestBody Empresa empresa) {
		return empresaRepository.save(empresa);
	}

	// ACTUALIZACION
	/**
	 * funcion para no repetir codigo de salvado de empresa
	 * 
	 * @param tempEmpresa empresa a buscar en la BBDD
	 * @param empresa     empresa a salvar
	 * @return Empresa
	 */
	Empresa saveEmpresa(Empresa tempEmpresa, Empresa empresa) {
		tempEmpresa.setSimbolo(empresa.getSimbolo());
		tempEmpresa.setNombre(empresa.getNombre());
		tempEmpresa.setSector(empresa.getSector());
		tempEmpresa.setCotizacion(empresa.getCotizacion());
		tempEmpresa.setDivisa(empresa.getDivisa());

		return empresaRepository.save(tempEmpresa);
	}

	@PutMapping("/simbolo/{simbolo}")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa updatebySymbol(@RequestBody Empresa empresa, @PathVariable("simbolo") String simbolo) {
		Empresa tempEmpresa = empresaRepository.findBySimbolo(simbolo);

		return saveEmpresa(tempEmpresa, empresa);
	}

	@PutMapping("/nombre/{nombre}")
	@ResponseStatus(HttpStatus.CREATED)
	public Empresa updateByName(@RequestBody Empresa empresa, @PathVariable("nombre") String nombre) {
		Empresa tempEmpresa = empresaRepository.findByNombre(nombre);

		return saveEmpresa(tempEmpresa, empresa);
	}

	// endpoint para la actualizacion de empresas por simbolo
	@PostMapping("/multiplesEmpresas")
    public List<Empresa> handleMultipleObjects(@RequestBody List<Empresa> empresas) {
        return empresaRepository.saveAll(empresas);
    }

	// DESACTIVACION
	@DeleteMapping("/desactivatedsimbolo/{simbolo}")
	public ResponseEntity<Empresa> desactivateById(@PathVariable("simbolo") String simbolo) {
		Empresa empresa = empresaRepository.findBySimbolo(simbolo);
		if (empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}
		empresa.setActivo(false);
		empresaRepository.save(empresa);
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}

	@DeleteMapping("/desactivatedname/{nombre}")
	public ResponseEntity<Empresa> desactivateByName(@PathVariable("nombre") String nombre) {
		Empresa empresa = empresaRepository.findByNombre(nombre);
		if (empresa == null) {
			return new ResponseEntity<Empresa>(HttpStatus.NOT_FOUND);
		}
		empresa.setActivo(false);
		empresaRepository.save(empresa);
		return new ResponseEntity<Empresa>(empresa, HttpStatus.OK);
	}

}
