/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.wsclinicauna.model;

import java.time.LocalDate;

/**
 *
 * @author ArauzKJ
 */
public class CliCitaDto {

    private Long citId;
    private String citUsuarioRegistra;
    private String citMotivo;
    private LocalDate citFechaHora;
    private Long citVersion;
    private CliAgendaDto cliAgendaDto;
    private CliPacienteDto cliPacienteDto;
    private Boolean modificado;

    public Long getCitId() {
        return citId;
    }

    public void setCitId(Long citId) {
        this.citId = citId;
    }

    public String getCitUsuarioRegistra() {
        return citUsuarioRegistra;
    }

    public void setCitUsuarioRegistra(String citUsuarioRegistra) {
        this.citUsuarioRegistra = citUsuarioRegistra;
    }

    public String getCitMotivo() {
        return citMotivo;
    }

    public void setCitMotivo(String citMotivo) {
        this.citMotivo = citMotivo;
    }

    public LocalDate getCitFechaHora() {
        return citFechaHora;
    }

    public void setCitFechaHora(LocalDate citFechaHora) {
        this.citFechaHora = citFechaHora;
    }

    public Long getCitVersion() {
        return citVersion;
    }

    public void setCitVersion(Long citVersion) {
        this.citVersion = citVersion;
    }

    public CliAgendaDto getCliAgendaDto() {
        return cliAgendaDto;
    }

    public void setCliAgendaDto(CliAgendaDto cliAgendaDto) {
        this.cliAgendaDto = cliAgendaDto;
    }

    public CliPacienteDto getCliPacienteDto() {
        return cliPacienteDto;
    }

    public void setCliPacienteDto(CliPacienteDto cliPacienteDto) {
        this.cliPacienteDto = cliPacienteDto;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

}
