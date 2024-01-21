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

import PROY.BAKENDAPI.domain.Broker;
import PROY.BAKENDAPI.repository.BrokerRepository;

@RestController
@RequestMapping("api/brokers")
public class BrokerController {

	@Autowired
	BrokerRepository brokerRepository;

	// LISTADO
	@GetMapping({ "/", "" })
	public List<Broker> getAllBrokers() {
		List<Broker> brokers = brokerRepository.findAll();
		return brokers;
	}

	// CONSULTAS
	@GetMapping("/id/{id}")
	public ResponseEntity<Broker> getById(@PathVariable("id") Long id) {
		Broker broker = brokerRepository.findById(id).orElse(null);
		if (broker == null) {
			return new ResponseEntity<Broker>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Broker>(broker, HttpStatus.OK);
	}

	@GetMapping("/nombre/{nombre}")
	public ResponseEntity<Broker> getByName(@PathVariable("nombre") String nombre) {
		Broker broker = brokerRepository.findByNombre(nombre);
		if (broker == null) {
			return new ResponseEntity<Broker>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Broker>(broker, HttpStatus.OK);
	}

	// CREACION - ACTUALIZACION
	@PostMapping({ "/", "" })
	@ResponseStatus(HttpStatus.CREATED)
	public Broker createOrUpdate(@RequestBody Broker broker) {
		return brokerRepository.save(broker);
	}

	// ACTUALIZACION
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Broker update(@RequestBody Broker broker, @PathVariable("id") Long id) {
		Broker tempBroker = brokerRepository.findById(id).orElse(null);

		tempBroker.setNombre(broker.getNombre());

		return brokerRepository.save(tempBroker);
	}

	// DESACTIVACION
	@DeleteMapping("/desactivatedid/{id}")
	public ResponseEntity<Broker> desactivateById(@PathVariable("id") Long id) {
		Broker broker = brokerRepository.findById(id).orElse(null);
		if(broker == null) {
			return new ResponseEntity<Broker>(HttpStatus.NOT_FOUND);
		}
		broker.setActivo(false);
		brokerRepository.save(broker);
		return new ResponseEntity<Broker>(broker, HttpStatus.OK);
	}

	@DeleteMapping("/desactivatedname/{nombre}")
	public ResponseEntity<Broker> desactivateByName(@PathVariable("nombre") String nombre) {
		Broker broker = brokerRepository.findByNombre(nombre);
		if(broker == null) {
			return new ResponseEntity<Broker>(HttpStatus.NOT_FOUND);
		}
		broker.setActivo(false);
		brokerRepository.save(broker);
		return new ResponseEntity<Broker>(broker, HttpStatus.OK);
	}

}
