package cr.ac.una.clinicauna.service;

import cr.ac.una.clinicauna.model.CliAgendaDto;
import cr.ac.una.clinicauna.model.CliCitaDto;
import cr.ac.una.clinicauna.model.CliMedicoDto;
import cr.ac.una.clinicauna.util.Request;
import cr.ac.una.clinicauna.util.Respuesta;
import jakarta.ws.rs.core.GenericType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author ANTHONY
 */
public class CliAgendaService {

    public Respuesta getAgenda(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CliAgendaController/agenda", "{id}", parametros);
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            CliAgendaDto agendaDto = (CliAgendaDto) request.readEntity(CliAgendaDto.class);
            return new Respuesta(true, "", "", "Agenda", agendaDto);
        } catch (Exception ex) {
            Logger.getLogger(CliAgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo la agenda [" + id + "]", ex);
            return new Respuesta(false, "key.errorObAgenda", "getAgenda " + ex.getMessage());
        }
    }

    public Respuesta getAgenda(CliMedicoDto medicoDto, LocalDate fecha) {
        try {
            Request request = new Request("CliAgendaController/agendas");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<CliAgendaDto> agendas = (List<CliAgendaDto>) request.readEntity(new GenericType<List<CliAgendaDto>>() {
            });
            Optional<CliAgendaDto> agendaFiltrada = agendas.stream()
                    .filter(agenda -> Objects.equals(agenda.getCliMedicoDto().getMedId(), medicoDto.getMedId()) && agenda.getAgeFecha().equals(fecha))
                    .findFirst();

            CliAgendaDto agendaEncontrada;
            if (agendaFiltrada.isPresent()) {
                agendaEncontrada = agendaFiltrada.get();
            } else {
                agendaEncontrada = null;
            }

            return new Respuesta(true, "", "", "Agenda", agendaEncontrada);
        } catch (Exception ex) {
            Logger.getLogger(CliAgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo agendas.", ex);
            return new Respuesta(false, "key.errorObAgenda", "getAgendas " + ex.getMessage());
        }
    }

    public Respuesta getAgendas(LocalDate fecha) {
        try {
            Request request = new Request("CliAgendaController/agendas");
            request.get();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            List<CliAgendaDto> agendas = (List<CliAgendaDto>) request.readEntity(new GenericType<List<CliAgendaDto>>() {
            });

            List<CliCitaDto> agendaFiltrada = agendas.stream()
                    .filter(agenda -> agenda.getAgeFecha().equals(fecha))
                    .flatMap(objeto -> objeto.getCliCitaList().stream())
                    .sorted((cita1, cita2) -> cita1.getCitFechaHora().compareTo(cita2.getCitFechaHora())) // Ordena por fecha y hora ascendente
                    .collect(Collectors.toList());

            return new Respuesta(true, "", "", "Agenda", agendaFiltrada);
        } catch (Exception ex) {
            Logger.getLogger(CliAgendaService.class.getName()).log(Level.SEVERE, "Error obteniendo agendas.", ex);
            return new Respuesta(false, "key.errorObAgenda", "getAgendas " + ex.getMessage());
        }
    }

    public Respuesta guardarAgenda(CliAgendaDto agenda) {
        try {
            Request request = new Request("CliAgendaController/agenda");
            request.post(agenda);
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            CliAgendaDto agendaDto = (CliAgendaDto) request.readEntity(CliAgendaDto.class);
            return new Respuesta(true, "", "", "Agenda", agendaDto);
        } catch (Exception ex) {
            Logger.getLogger(CliAgendaService.class.getName()).log(Level.SEVERE, "Error guardando el agenda.", ex);
            return new Respuesta(false, "key.errorSaveAgenda", "guardarAgenda " + ex.getMessage());
        }
    }

    public Respuesta eliminarAgenda(Long id) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("id", id);
            Request request = new Request("CliAgendaController/eliminarAgenda", "{id}", parametros);
            request.delete();
            if (request.isError()) {
                return new Respuesta(false, request.getError(), "");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            Logger.getLogger(CliAgendaService.class.getName()).log(Level.SEVERE, "Error eliminando el agenda.", ex);
            return new Respuesta(false, "key.errorDelAgenda", "eliminarAgenda " + ex.getMessage());
        }
    }
}
