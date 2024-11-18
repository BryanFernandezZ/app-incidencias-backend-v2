package com.example.app_incidencias_backend_v2.dao;

import com.example.app_incidencias_backend_v2.dto.request.IncidenciaRequestDto;
import com.example.app_incidencias_backend_v2.dto.response.IncidenciaResponseDto;
import com.example.app_incidencias_backend_v2.util.CloudinaryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class IncidenciasDao {

    private final Logger logger = LoggerFactory.getLogger(IncidenciasDao.class);

    private final JdbcTemplate jdbcTemplate;
    private final CloudinaryUtil cloudinaryUtil;

    public IncidenciasDao(JdbcTemplate jdbcTemplate, CloudinaryUtil cloudinaryUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.cloudinaryUtil = cloudinaryUtil;
    }

    public List<Object> obtenerIncidenciasPorCliente(Integer idCliente) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ObtenerIncidenciasPorCliente")
                    .declareParameters(new SqlParameter("_id_cliente", Types.INTEGER));

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_cliente", idCliente);

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.get("#result-set-1").toString());
            return (List<Object>) response.get("#result-set-1");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public Object obtenerIncidenciaPorId(Integer idIncidencia) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ObtenerIncidenciasPorId")
                    .declareParameters(new SqlParameter("_id_incidencia", Types.INTEGER));

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_incidencia", idIncidencia);

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.get("#result-set-1").toString());
            List<Object> recordSet = (List<Object>) response.get("#result-set-1");
            if (!recordSet.isEmpty()) {
                return recordSet.get(0);
            } else throw new RuntimeException("No existe una incidencia con el id " + idIncidencia);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void registrarIncidencia(IncidenciaRequestDto incidenciaRequestDto, MultipartFile image) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("RegistrarIncidencia")
                    .declareParameters(
                            new SqlParameter("_asunto", Types.VARCHAR),
                            new SqlParameter("_detalle", Types.VARCHAR),
                            new SqlParameter("_imagen", Types.VARCHAR),
                            new SqlParameter("_usuario_asignado", Types.INTEGER),
                            new SqlParameter("_id_contrato", Types.INTEGER),
                            new SqlOutParameter("_id_incidencia", Types.INTEGER)
                    );

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_asunto", incidenciaRequestDto.getAsunto());
            parameters.put("_detalle", incidenciaRequestDto.getDetalle());
            parameters.put("_imagen", null); // TEMPORAL
            parameters.put("_usuario_asignado", incidenciaRequestDto.getUsuarioAsignado());
            parameters.put("_id_contrato", incidenciaRequestDto.getIdContrato());

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());

            Integer idIncidencia = (Integer) response.get("_id_incidencia");
            String imageUrl = cloudinaryUtil.guardarImagenEnNube(image, idIncidencia);

            actualizarImagenIncidencia(idIncidencia, imageUrl);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void actualizarIncidencia(Integer idIncidencia, IncidenciaRequestDto incidenciaRequestDto, MultipartFile image) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            String imageUrl = cloudinaryUtil.actualizarImagenEnLaNube(image, idIncidencia);

            simpleJdbcCall.withProcedureName("ActualizarIncidencia")
                    .declareParameters(
                            new SqlParameter("_id_incidencia", Types.INTEGER),
                            new SqlParameter("_asunto", Types.VARCHAR),
                            new SqlParameter("_detalle", Types.VARCHAR),
                            new SqlParameter("_imagen", Types.VARCHAR),
                            new SqlParameter("_usuario_asignado", Types.INTEGER),
                            new SqlParameter("_id_contrato", Types.INTEGER)
                    );

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_incidencia", idIncidencia);
            parameters.put("_asunto", incidenciaRequestDto.getAsunto());
            parameters.put("_detalle", incidenciaRequestDto.getDetalle());
            parameters.put("_imagen", imageUrl);
            parameters.put("_usuario_asignado", incidenciaRequestDto.getUsuarioAsignado());
            parameters.put("_id_contrato", incidenciaRequestDto.getIdContrato());

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void actualizarImagenIncidencia(Integer idIncidencia, String imageUrl) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        try {
            simpleJdbcCall.withProcedureName("ActualizarImagenIncidencia")
                    .declareParameters(
                            new SqlParameter("_id_incidencia", Types.INTEGER),
                            new SqlParameter("_imagen", Types.VARCHAR)
                    );

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("_id_incidencia", idIncidencia);
            parameters.put("_imagen", imageUrl);

            Map<String, Object> response = simpleJdbcCall.execute(parameters);
            logger.debug(response.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
