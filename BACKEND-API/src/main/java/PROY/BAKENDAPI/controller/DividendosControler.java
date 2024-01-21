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

import PROY.BAKENDAPI.domain.Dividendos;
import PROY.BAKENDAPI.repository.DividendosRepository;

@RestController
@RequestMapping("api/dividendos")
public class DividendosControler {

    @Autowired
    DividendosRepository dividendosRepository;

    // LISTADO

    // CONSULTAS
    // Consulta Dividendos por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<Dividendos> getDividendoById(@PathVariable Integer id) {
        Dividendos dividendo = dividendosRepository.findById(id).orElse(null);
        if (dividendo != null) {
            return new ResponseEntity<>(dividendo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Consulta dividendos por Usuario
    @GetMapping("/idusuario/{idUsuario}")
    public ResponseEntity<List<Dividendos>> getDividendosByUsuario(@PathVariable Long idUsuario) {
        List<Dividendos> dividendos = dividendosRepository.findByUsuarioId(idUsuario);
        if (!dividendos.isEmpty()) {
            return new ResponseEntity<>(dividendos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Consulta por Usuario y Simbolopara dividendos
    @GetMapping("/idusuario/{idUsuario}/simbolo/{simbolo}")
    public ResponseEntity<List<Dividendos>> getDividendosBySimboloAndUsuario(
            @PathVariable String simbolo, @PathVariable Long idUsuario) {
        List<Dividendos> dividendos = dividendosRepository.findBySimboloAndUsuarioId(simbolo, idUsuario);
        if (!dividendos.isEmpty()) {
            return new ResponseEntity<>(dividendos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // consulta por usuario y fecha
    @GetMapping("/idusuario/{idusuario}/fecha/{fecha}")
    public ResponseEntity<List<Dividendos>> getByUserIdDate(@PathVariable("idusuario") Long idusuario,
            @PathVariable("fecha") String fecha) {
        LocalDate date1 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }
        List<Dividendos> dividendos = dividendosRepository.findByUsuarioId(idusuario);
        if (dividendos == null) {
            return new ResponseEntity<List<Dividendos>>(HttpStatus.NOT_FOUND);
        }

        Iterator<Dividendos> iterator = dividendos.iterator();

        while (iterator.hasNext()) {
            Dividendos dividendo = iterator.next();
            if (dividendo.getFecha().compareTo(Date.valueOf(date1)) != 0) {
                iterator.remove();
            }
        }
        return new ResponseEntity<List<Dividendos>>(dividendos, HttpStatus.OK);
    }

    // consulta por usuario, fecha y simbolo
    @GetMapping("/idusuario/{idusuario}/simbolo/{simbolo}/fecha/{fecha}")
    public ResponseEntity<List<Dividendos>> getByUserIdDateAndSymbol(@PathVariable("idusuario") Long idusuario,
            @PathVariable("simbolo") String simbolo,
            @PathVariable("fecha") String fecha) {
        LocalDate date1 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }
        List<Dividendos> dividendos = dividendosRepository.findByUsuarioIdAndFechaAndSimbolo(idusuario,
                Date.valueOf(date1), simbolo);
        if (dividendos == null) {
            return new ResponseEntity<List<Dividendos>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Dividendos>>(dividendos, HttpStatus.OK);
    }

    // consulta por usuario y 2 fechas
    @GetMapping("/idusuario/{idusuario}/fecha/{fecha}/fecha2/{fecha2}")
    public ResponseEntity<List<Dividendos>> getByUserIdBetween2Dates(@PathVariable("idusuario") Long idusuario,
            @PathVariable("fecha") String fecha, @PathVariable("fecha2") String fecha2) {
        LocalDate date1 = LocalDate.MIN, date2 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
            date2 = LocalDate.parse(fecha2, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }
        List<Dividendos> dividendos = dividendosRepository.findByUsuarioId(idusuario);
        if (dividendos == null) {
            return new ResponseEntity<List<Dividendos>>(HttpStatus.NOT_FOUND);
        }

        Iterator<Dividendos> iterator = dividendos.iterator();

        while (iterator.hasNext()) {
            Dividendos dividendo = iterator.next();
            if (dividendo.getFecha().compareTo(Date.valueOf(date1)) <= 0
                    && dividendo.getFecha().compareTo(Date.valueOf(date2)) >= 0) {
                iterator.remove();
            }
        }
        return new ResponseEntity<List<Dividendos>>(dividendos, HttpStatus.OK);
    }

    // consulta por usuario, 2 fechas y simbolo
    @GetMapping("/idusuario/{idusuario}/simbolo/{simbolo}/fecha/{fecha}/fecha2/{fecha2}")
    public ResponseEntity<List<Dividendos>> getByUserIdBetween2DatesAndSymbol(@PathVariable("idusuario") Long idusuario,
            @PathVariable("simbolo") String simbolo,
            @PathVariable("fecha") String fecha, @PathVariable("fecha2") String fecha2) {
        LocalDate date1 = LocalDate.MIN, date2 = LocalDate.MIN;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date1 = LocalDate.parse(fecha, formatter);
            date2 = LocalDate.parse(fecha2, formatter);
        } catch (Exception e) {
            System.out.println("ERROR->" + e.getMessage());
        }
        List<Dividendos> dividendos = dividendosRepository.findByUsuarioIdAndFechaBetweenAndSimbolo(idusuario,
                Date.valueOf(date1), Date.valueOf(date2), simbolo);
        if (dividendos == null) {
            return new ResponseEntity<List<Dividendos>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Dividendos>>(dividendos, HttpStatus.OK);
    }

    /**
     * Funcion para ordenar la lista de dividendos a devolver por la API
     * 
     * @param dividendos lista de dividendos encontrada
     * @param orderType
     *                   1 - Ordenacion por fecha
     *                   2 - Ordenacion por operacion
     *                   3 - Ordenacion por importe
     * @param downward
     *                   1 - Orden descendente
     *                   0 - Orden ascendente
     */
    private void ordenarDividendos(List<Dividendos> dividendos, Integer orderType, Integer downward) {
        switch (orderType) {
            case 1: // fecha
                if (downward == 1)
                    Collections.sort(dividendos, Comparator.comparing(Dividendos::getFecha));
                else
                    Collections.sort(dividendos, Comparator.comparing(Dividendos::getFecha).reversed());
                break;
            case 2: // operacion
                if (downward == 1)
                    Collections.sort(dividendos, Comparator.comparing(
                            div -> div.getSimbolo() != null ? div.getSimbolo() : ""));
                else
                    Collections.sort(dividendos, Comparator.comparing(
                            div -> ((Dividendos) div).getSimbolo() != null
                                    ? ((Dividendos) div).getSimbolo()
                                    : "")
                            .reversed());
                break;
                case 3: // importe
                Collections.sort(dividendos, Comparator.comparingDouble(div -> div.getPrecioAccion().doubleValue()));
				if (downward == 0)
                    Collections.reverse(dividendos);
				break;
        }
    }

    @GetMapping("/idusuario/{idusuario}/ordertype/{ordertype}/downward/{downward}")
    public ResponseEntity<List<Dividendos>> getByUserId(@PathVariable("idusuario") Long idusuario,
            @PathVariable("ordertype") Integer orderType, @PathVariable("downward") Integer downward) {
        List<Dividendos> dividendos = dividendosRepository.findByUsuarioId(idusuario);
        if (dividendos == null) {
            return new ResponseEntity<List<Dividendos>>(HttpStatus.NOT_FOUND);
        }
        ordenarDividendos(dividendos, orderType, downward);
        return new ResponseEntity<List<Dividendos>>(dividendos, HttpStatus.OK);
    }

    // CREACION - ACTUALIZACION
    @PostMapping({ "/", "" })
    @ResponseStatus(HttpStatus.CREATED)
    public Dividendos createOrUpdate(@RequestBody Dividendos dividendos) {
        return dividendosRepository.save(dividendos);
    }

    // ACTUALIZACION
    // Actualiza un dividendo segun el id
    /**
     * Función para no repetir el código de salvado de dividendos.
     * 
     * @param tempDividendo dividendo a buscar en la BBDD
     * @param dividendo     dividendo a salvar
     * @return Dividendos
     */
    Dividendos saveDividendo(Dividendos tempDividendo, Dividendos dividendo) {
        tempDividendo.setSimbolo(dividendo.getSimbolo());
        tempDividendo.setBroker(dividendo.getBroker());
        tempDividendo.setUsuario(dividendo.getUsuario());
        tempDividendo.setFecha(dividendo.getFecha());
        tempDividendo.setPrecioAccion(dividendo.getPrecioAccion());
        tempDividendo.setRetencion(dividendo.getRetencion());

        return dividendosRepository.save(tempDividendo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Dividendos updateById(@RequestBody Dividendos dividendos, @PathVariable("id") Integer id) {
        Dividendos tempDividendo = dividendosRepository.findById(id).orElse(null);

        if (tempDividendo != null) {
            return saveDividendo(tempDividendo, dividendos);
        } else {
            return null;
        }
    }

    // ELIMINACIÓN
    // Eliminación por id
    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) {
        dividendosRepository.deleteById(id);
    }

}
