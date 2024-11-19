package com.example.app_incidencias_backend_v2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class ClienteDao {

    private final Logger logger = LoggerFactory.getLogger(ClienteDao.class);

    private final JdbcTemplate jdbcTemplate;

    public ClienteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Object> listarClientes() {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ListarClientes").withoutProcedureColumnMetaDataAccess();
            Map<String, Object> response = simpleJdbcCall.execute();
            logger.debug(response.toString());
            return (List<Object>) response.get("#result-set-1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
