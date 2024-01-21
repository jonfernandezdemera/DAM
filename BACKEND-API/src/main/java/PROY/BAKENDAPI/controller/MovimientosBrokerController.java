package PROY.BAKENDAPI.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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

import PROY.BAKENDAPI.domain.MovimientosBroker;
import PROY.BAKENDAPI.repository.MovimientosBrokerRepository;

@RestController
@RequestMapping("api/movimientos")
public class MovimientosBrokerController {

	@Autowired
	MovimientosBrokerRepository movimientosBrokerRepository;

	// LISTADO
	@GetMapping({ "/", "" })
	public List<MovimientosBroker> getAllBrokerMovements() {
		List<MovimientosBroker> movimientosBroker = movimientosBrokerRepository.findAll();
		return movimientosBroker;
	}

	// CONSULTAS
	@GetMapping("/id/{id}")
	public ResponseEntity<MovimientosBroker> getById(@PathVariable("id") Long id) {
		MovimientosBroker movimientosBroker = movimientosBrokerRepository.findById(id).orElse(null);
		if (movimientosBroker == null) {
			return new ResponseEntity<MovimientosBroker>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MovimientosBroker>(movimientosBroker, HttpStatus.OK);
	}

	@GetMapping("/idusuario/{idusuario}")
	public ResponseEntity<List<MovimientosBroker>> getByUserId(@PathVariable("idusuario") Long idusuario) {
		List<MovimientosBroker> movimientos = movimientosBrokerRepository.findByUsuarioId(idusuario);
		if (movimientos == null) {
			return new ResponseEntity<List<MovimientosBroker>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MovimientosBroker>>(movimientos, HttpStatus.OK);
	}

	@GetMapping("/idbroker/{idbroker}")
	public ResponseEntity<List<MovimientosBroker>> getByBrokerId(@PathVariable("idbroker") Long idbroker) {
		List<MovimientosBroker> movimientos = movimientosBrokerRepository.findByBrokerId(idbroker);
		if (movimientos == null) {
			return new ResponseEntity<List<MovimientosBroker>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<MovimientosBroker>>(movimientos, HttpStatus.OK);
	}

	/**
	 * Funcion para ordenar la lista de movimientos a devolver por la API
	 * 
	 * @param movimientos lista de movimientos encontrada
	 * @param orderType
	 *                    1 - Ordenacion por fecha
	 *                    2 - Ordenacion por operacion
	 *                    3 - Ordenacion por importe
	 * @param downward
	 *                    1 - Orden descendente
	 *                    0 - Orden ascendente
	 */
	private void ordenarMovimientos(List<MovimientosBroker> movimientos, Integer orderType, Integer downward) {
		switch (orderType) {
			case 1: // fecha
				if (downward == 1)
					Collections.sort(movimientos, Comparator.comparing(MovimientosBroker::getFecha));
				else
					Collections.sort(movimientos, Comparator.comparing(MovimientosBroker::getFecha).reversed());
				break;
			case 2: // operacion
				if (downward == 1)
					Collections.sort(movimientos, Comparator.comparingLong(
							mov -> mov.getIdoperacion() != null ? mov.getIdoperacion() : Long.MAX_VALUE));
				else
					Collections.sort(movimientos, Comparator.comparingLong(
							mov -> ((MovimientosBroker) mov).getIdoperacion() != null
									? ((MovimientosBroker) mov).getIdoperacion()
									: Long.MAX_VALUE)
							.reversed());
				break;
			case 3: // importe
				if (downward == 1)
					Collections.sort(movimientos, Comparator.comparingDouble(MovimientosBroker::getImporte));
				else
					Collections.sort(movimientos, Comparator.comparingDouble(MovimientosBroker::getImporte).reversed());
				break;
		}
	}

	@GetMapping("/idusuario/{idusuario}/ordertype/{ordertype}/downward/{downward}")
	public ResponseEntity<List<MovimientosBroker>> getByUserId(@PathVariable("idusuario") Long idusuario,
			@PathVariable("ordertype") Integer orderType, @PathVariable("downward") Integer downward) {
		List<MovimientosBroker> movimientos = movimientosBrokerRepository.findByusuarioId(idusuario);
		if (movimientos == null) {
			return new ResponseEntity<List<MovimientosBroker>>(HttpStatus.NOT_FOUND);
		}
		ordenarMovimientos(movimientos, orderType, downward);
		return new ResponseEntity<List<MovimientosBroker>>(movimientos, HttpStatus.OK);
	}

	@GetMapping("/idusuario/{idusuario}/operacion/{operacion}")
	public ResponseEntity<List<MovimientosBroker>> getByUserIdOperation(@PathVariable("idusuario") Long idusuario,
			@PathVariable("operacion") Integer operacion) {
		List<MovimientosBroker> movimientos = movimientosBrokerRepository.findByusuarioId(idusuario);
		if (movimientos == null) {
			return new ResponseEntity<List<MovimientosBroker>>(HttpStatus.NOT_FOUND);
		}

		Iterator<MovimientosBroker> iterator = movimientos.iterator();

		while (iterator.hasNext()) {
			MovimientosBroker movimiento = iterator.next();
			if (movimiento.getOperacion() != operacion) {
				iterator.remove();
			}
		}
		return new ResponseEntity<List<MovimientosBroker>>(movimientos, HttpStatus.OK);
	}

	@GetMapping("/idusuario/{idusuario}/fecha/{fecha}")
	public ResponseEntity<List<MovimientosBroker>> getByUserIdDate(@PathVariable("idusuario") Long idusuario,
			@PathVariable("fecha") String fecha) {
		LocalDate date1 = LocalDate.MIN;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			date1 = LocalDate.parse(fecha, formatter);
		} catch (Exception e) {
			System.out.println("ERROR->" + e.getMessage());
		}
		List<MovimientosBroker> movimientos = movimientosBrokerRepository.findByusuarioId(idusuario);
		if (movimientos == null) {
			return new ResponseEntity<List<MovimientosBroker>>(HttpStatus.NOT_FOUND);
		}

		Iterator<MovimientosBroker> iterator = movimientos.iterator();

		while (iterator.hasNext()) {
			MovimientosBroker movimiento = iterator.next();
			if (movimiento.getFecha().compareTo((Date.valueOf(date1))) != 0) {
				iterator.remove();
			}
		}
		return new ResponseEntity<List<MovimientosBroker>>(movimientos, HttpStatus.OK);
	}

	@GetMapping("/idusuario/{idusuario}/fecha/{fecha}/fecha2/{fecha2}")
	public ResponseEntity<List<MovimientosBroker>> getByUserIdBetween2Dates(@PathVariable("idusuario") Long idusuario,
			@PathVariable("fecha") String fecha, @PathVariable("fecha2") String fecha2) {
		LocalDate date1 = LocalDate.MIN, date2 = LocalDate.MIN;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			date1 = LocalDate.parse(fecha, formatter);
			date2 = LocalDate.parse(fecha2, formatter);
		} catch (Exception e) {
			System.out.println("ERROR->" + e.getMessage());
		}
		List<MovimientosBroker> movimientos = movimientosBrokerRepository.findByusuarioId(idusuario);
		if (movimientos == null) {
			return new ResponseEntity<List<MovimientosBroker>>(HttpStatus.NOT_FOUND);
		}

		Iterator<MovimientosBroker> iterator = movimientos.iterator();

		while (iterator.hasNext()) {
			MovimientosBroker movimiento = iterator.next();
			if (movimiento.getFecha().compareTo((Date.valueOf(date1))) <= 0
					&& movimiento.getFecha().compareTo((Date.valueOf(date2))) >= 0) {
				iterator.remove();
			}
		}
		return new ResponseEntity<List<MovimientosBroker>>(movimientos, HttpStatus.OK);
	}

	// CREACION - ACTUALIZACION
	@PostMapping({ "/", "" })
	@ResponseStatus(HttpStatus.CREATED)
	public MovimientosBroker createOrUpdate(@RequestBody MovimientosBroker movimiento) {
		return movimientosBrokerRepository.save(movimiento);
	}

	// ACTUALIZACION
	// Actualiza una transacción segun el id
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public MovimientosBroker update(@RequestBody MovimientosBroker movimiento, @PathVariable("id") Long id) {
		MovimientosBroker tempMovimiento = movimientosBrokerRepository.findById(id).orElse(null);

		if (tempMovimiento != null) {
			tempMovimiento.setBroker(movimiento.getBroker());
			tempMovimiento.setUsuario(movimiento.getUsuario());
			tempMovimiento.setOperacion(movimiento.getOperacion());
			tempMovimiento.setFecha(movimiento.getFecha());
			tempMovimiento.setComision(movimiento.getComision());
			tempMovimiento.setImporte(movimiento.getImporte());

			return movimientosBrokerRepository.save(tempMovimiento);
		} else {
			return null;
		}
	}

	// ELIMINACION
	@DeleteMapping("/id/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable("id") Long id) {
		movimientosBrokerRepository.deleteById(id);
	}

}
