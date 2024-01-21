package PROY.BAKENDAPI.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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

import PROY.BAKENDAPI.domain.Transacciones;
import PROY.BAKENDAPI.repository.TransaccionesRepository;

@RestController
@RequestMapping("api/transacciones")
public class TransaccionesController {

    @Autowired
    TransaccionesRepository transaccionesRepository;

    // LISTADO

    // CONSULTAS
    // Consulta de Transacciones por ID de Usuario
    @GetMapping("/idusuario/{idUsuario}")
    public ResponseEntity<List<Transacciones>> getTransaccionesByUsuario(@PathVariable Long idUsuario) {
        List<Transacciones> transacciones = transaccionesRepository.findByUsuarioId(idUsuario);
        if (!transacciones.isEmpty()) {
            return new ResponseEntity<>(transacciones, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Consulta de Transacciones por ID de Usuario
    @GetMapping("/idtransaccion/{id}")
    public ResponseEntity<Transacciones> getTransaccionesById(@PathVariable Long id) {
        Transacciones transaccion = transaccionesRepository.findById(id).orElse(null);
        if (transaccion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Transacciones>(transaccion, HttpStatus.NOT_FOUND);
    }

    // Consulta de Transacciones por Usuario y Broker
    @GetMapping("/idusuario/{idUsuario}/idbroker/{idBroker}")
    public ResponseEntity<List<Transacciones>> getTransaccionesByUsuarioAndBroker(
            @PathVariable Long idUsuario, @PathVariable Long idBroker) {
        List<Transacciones> transacciones = transaccionesRepository.findByUsuarioIdAndBrokerId(idUsuario, idBroker);
        if (!transacciones.isEmpty()) {
            return new ResponseEntity<>(transacciones, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Consulta de Transacciones por Usuario y tipo de operación
    @GetMapping("/idusuario/{idUsuario}/operacion/{operacion}")
    public ResponseEntity<List<Transacciones>> getTransaccionesByUsuarioAndOperacion(
            @PathVariable Long idUsuario, @PathVariable Integer operacion) {
        List<Transacciones> transacciones = transaccionesRepository.findByUsuarioIdAndOperacion(idUsuario, operacion);
        if (!transacciones.isEmpty()) {
            return new ResponseEntity<>(transacciones, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // consulta por usuario y fecha
    @GetMapping("/idusuario/{idusuario}/fecha/{fecha}")
    public ResponseEntity<List<Transacciones>> getByUserIdDate(@PathVariable("idusuario") Long idusuario,
            @PathVariable("fecha") String fecha) {
        LocalDate date1 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }

        List<Transacciones> transacciones = transaccionesRepository.findByUsuarioId(idusuario);
        if (transacciones == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterator<Transacciones> iterator = transacciones.iterator();

        while (iterator.hasNext()) {
            Transacciones transaccion = iterator.next();
            if (transaccion.getFecha().compareTo(Date.valueOf(date1)) != 0) {
                iterator.remove();
            }
        }
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }

    // consulta por usuario y 2 fechas
    @GetMapping("/idusuario/{idusuario}/fecha/{fecha}/fecha2/{fecha2}")
    public ResponseEntity<List<Transacciones>> getByUserIdBetween2Dates(
            @PathVariable("idusuario") Long idusuario, @PathVariable("fecha") String fecha,
            @PathVariable("fecha2") String fecha2) {

        LocalDate date1 = LocalDate.MIN, date2 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
            date2 = LocalDate.parse(fecha2, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }

        List<Transacciones> transacciones = transaccionesRepository.findByUsuarioId(idusuario);
        if (transacciones == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterator<Transacciones> iterator = transacciones.iterator();

        while (iterator.hasNext()) {
            Transacciones transaccion = iterator.next();
            LocalDate transaccionDate = transaccion.getFecha().toLocalDate();
            if (!(transaccionDate.isEqual(date1) || transaccionDate.isEqual(date2) ||
                    (transaccionDate.isAfter(date1) && transaccionDate.isBefore(date2)))) {
                iterator.remove();
            }
        }

        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }

    // Por usuario, broker y entre dos fechas
    @GetMapping("/idusuario/{idusuario}/idbroker/{idbroker}/fecha/{fecha}/fecha2/{fecha2}")
    public ResponseEntity<List<Transacciones>> getByUserAndBrokerBetween2Dates(
            @PathVariable("idusuario") Long idusuario, @PathVariable("idbroker") Long idbroker,
            @PathVariable("fecha") String fecha,
            @PathVariable("fecha2") String fecha2) {

        LocalDate date1 = LocalDate.MIN, date2 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
            date2 = LocalDate.parse(fecha2, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }

        List<Transacciones> transacciones = transaccionesRepository.findByUsuarioIdAndBrokerId(idusuario, idbroker);
        if (transacciones == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Iterator<Transacciones> iterator = transacciones.iterator();

        while (iterator.hasNext()) {
            Transacciones transaccion = iterator.next();
            LocalDate transaccionDate = transaccion.getFecha().toLocalDate();
            if (!(transaccionDate.isEqual(date1) || transaccionDate.isEqual(date2) ||
                    (transaccionDate.isAfter(date1) && transaccionDate.isBefore(date2)))) {
                iterator.remove();
            }
        }

        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }

    /**
	 * Funcion para ordenar la lista de movimientos a devolver por la API
	 * 
	 * @param transacciones lista de transacciones encontrada
	 * @param orderType
	 *                    1 - Ordenacion por fecha
	 *                    2 - Ordenacion por operacion
	 *                    3 - Ordenacion por importe
	 * @param downward
	 *                    1 - Orden descendente
	 *                    0 - Orden ascendente
	 */
	private void ordenarTransacciones(List<Transacciones> transacciones, Integer orderType, Integer downward) {
		switch (orderType) {
			case 1: // fecha
				if (downward == 1)
					Collections.sort(transacciones, Comparator.comparing(Transacciones::getFecha));
				else
					Collections.sort(transacciones, Comparator.comparing(Transacciones::getFecha).reversed());
				break;
			case 2: // operacion
				if (downward == 1)
					Collections.sort(transacciones, Comparator.comparingLong(
							tra -> tra.getOperacion() != null ? tra.getOperacion() : Long.MAX_VALUE));
				else
					Collections.sort(transacciones, Comparator.comparingLong(
							tra -> ((Transacciones) tra).getOperacion() != null
									? ((Transacciones) tra).getOperacion()
									: Long.MAX_VALUE)
							.reversed());
				break;
			case 3: // importe
                Collections.sort(transacciones, Comparator.comparingDouble(tra -> tra.getNumAcciones().doubleValue() * tra.getPrecioAccion().doubleValue()));
				if (downward == 0)
                    Collections.reverse(transacciones);
				break;
		}
	}

	@GetMapping("/idusuario/{idusuario}/ordertype/{ordertype}/downward/{downward}")
	public ResponseEntity<List<Transacciones>> getByUserId(@PathVariable("idusuario") Long idusuario,
			@PathVariable("ordertype") Integer orderType, @PathVariable("downward") Integer downward) {
		List<Transacciones> transacciones = transaccionesRepository.findByUsuarioId(idusuario);
		if (transacciones == null) {
			return new ResponseEntity<List<Transacciones>>(HttpStatus.NOT_FOUND);
		}
		ordenarTransacciones(transacciones, orderType, downward);
		return new ResponseEntity<List<Transacciones>>(transacciones, HttpStatus.OK);
	}

    // CREACION - ACTUALIZACION
    @PostMapping({ "/", "" })
    @ResponseStatus(HttpStatus.CREATED)
    public Transacciones createOrUpdate(@RequestBody Transacciones transacciones) {
        return transaccionesRepository.save(transacciones);
    }

    // ACTUALIZACION
    // Actualiza una transacción segun el id
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Transacciones update(@RequestBody Transacciones transaccion, @PathVariable("id") Long id) {
        Transacciones tempTransaccion = transaccionesRepository.findById(id).orElse(null);

        if (tempTransaccion != null) {
            tempTransaccion.setBroker(transaccion.getBroker());
            tempTransaccion.setUsuario(transaccion.getUsuario());
            tempTransaccion.setSimbolo(transaccion.getSimbolo());
            tempTransaccion.setOperacion(transaccion.getOperacion());
            tempTransaccion.setFecha(transaccion.getFecha());
            tempTransaccion.setNumAcciones(transaccion.getNumAcciones());
            tempTransaccion.setPrecioAccion(transaccion.getPrecioAccion());

            return transaccionesRepository.save(tempTransaccion);
        } else {
            return null;
        }
    }

    // ELIMINACIÓN
    // Eliminar una transacción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaccionById(@PathVariable("id") Long id) {
        Transacciones transaccion = transaccionesRepository.findById(id).orElse(null);

        if (transaccion != null) {
            transaccionesRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}