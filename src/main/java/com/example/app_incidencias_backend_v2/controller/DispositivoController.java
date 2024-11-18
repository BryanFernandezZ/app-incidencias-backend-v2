package com.example.app_incidencias_backend_v2.controller;

import com.example.app_incidencias_backend_v2.service.DispositivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/dispositivos")
public class DispositivoController {

    private final DispositivoService dispositivoService;

    public DispositivoController(DispositivoService dispositivoService) {
        this.dispositivoService = dispositivoService;
    }

    @RequestMapping(path = "/cliente/{id}", method = RequestMethod.GET)
    private ResponseEntity<?> obtenerDispositivosPorCliente(@PathVariable("id") Integer idCliente) {
        return ResponseEntity.ok(dispositivoService.obtenerDispositivosPorCliente(idCliente));
    }
}
