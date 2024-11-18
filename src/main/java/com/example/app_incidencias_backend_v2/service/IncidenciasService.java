package com.example.app_incidencias_backend_v2.service;

import com.example.app_incidencias_backend_v2.dao.IncidenciasDao;
import com.example.app_incidencias_backend_v2.dto.request.IncidenciaRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class IncidenciasService {

    private final IncidenciasDao incidenciasDao;

    public IncidenciasService(IncidenciasDao incidenciasDao) {
        this.incidenciasDao = incidenciasDao;
    }

    public List<Object> obtenerIncidenciasPorCliente(Integer idCliente) {
        return incidenciasDao.obtenerIncidenciasPorCliente(idCliente);
    }

    public Object obtenerIncidenciaPorId(Integer idIncidencia) {
        return incidenciasDao.obtenerIncidenciaPorId(idIncidencia);
    }

    public void registrarIncidencia(String incidenciaJson, MultipartFile image) {
        ObjectMapper mapper = new ObjectMapper();
        IncidenciaRequestDto incidencia;
        try {
            incidencia = mapper.readValue(incidenciaJson, IncidenciaRequestDto.class);
            incidenciasDao.registrarIncidencia(incidencia, image);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarIncidencia(Integer idIncidencia, String incidenciaJson, MultipartFile image) {
        ObjectMapper mapper = new ObjectMapper();
        IncidenciaRequestDto incidencia;
        try {
            incidencia = mapper.readValue(incidenciaJson, IncidenciaRequestDto.class);
            incidenciasDao.actualizarIncidencia(idIncidencia, incidencia, image);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
