package com.example.app_incidencias_backend_v2.controller;

import com.example.app_incidencias_backend_v2.service.IncidenciasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1/incidencias")
public class IncidenciasController {

    private final IncidenciasService incidenciasService;

    public IncidenciasController(IncidenciasService incidenciasService) {
        this.incidenciasService = incidenciasService;
    }

    @RequestMapping(path = "/cliente/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> obtenerIncidenciasPorCliente(@PathVariable("id") Integer idCliente) {
        return ResponseEntity.ok(incidenciasService.obtenerIncidenciasPorCliente(idCliente));
    }

    @RequestMapping(path = "/obtener/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> obtenerIncidenciaPorId(@PathVariable("id") Integer idIncidencia) {
        return ResponseEntity.ok(incidenciasService.obtenerIncidenciaPorId(idIncidencia));
    }

    @RequestMapping(path = "/registrar", method = RequestMethod.POST)
    private ResponseEntity<?> registrarIncidencia(
            @RequestParam("incidencia") String incidencia,
            @RequestParam("imagen") MultipartFile imagen
    ) {
        incidenciasService.registrarIncidencia(incidencia, imagen);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/actualizar/{id}", method = RequestMethod.PUT)
    private ResponseEntity<?> actualizarIncidencia(
            @PathVariable("id") Integer idIncidencia,
            @RequestParam("incidencia") String incidencia,
            @RequestParam("imagen") MultipartFile imagen
    ) {
        incidenciasService.actualizarIncidencia(idIncidencia, incidencia, imagen);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}