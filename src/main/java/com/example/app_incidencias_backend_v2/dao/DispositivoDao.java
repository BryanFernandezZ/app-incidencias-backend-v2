package com.example.app_incidencias_backend_v2.dao;

import com.example.app_incidencias_backend_v2.dto.response.DispositivoResponseDto;
import com.example.app_incidencias_backend_v2.dto.response.IncidenciaResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DispositivoDao {

    private final Logger logger = LoggerFactory.getLogger(DispositivoDao.class);

    private final JdbcTemplate jdbcTemplate;

    public DispositivoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Object> obtenerDispositivosPorCliente(Integer idCliente) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ObtenerDispositivosPorCliente")
                    .declareParameters(new SqlParameter("_id_cliente", Types.INTEGER));

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_cliente", idCliente);

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
            return (List<Object>) response.get("#result-set-1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
