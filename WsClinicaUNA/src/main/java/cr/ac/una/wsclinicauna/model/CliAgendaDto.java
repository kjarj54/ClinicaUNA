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
public class CliAgendaDto {
    private Long ageId;
    private LocalDate ageFecha;
    private String ageTiempo;
    private Long ageEspacios;
    private Long ageVersion;

    public Long getAgeId() {
        return ageId;
    }

    public void setAgeId(Long ageId) {
        this.ageId = ageId;
    }

    public LocalDate getAgeFecha() {
        return ageFecha;
    }

    public void setAgeFecha(LocalDate ageFecha) {
        this.ageFecha = ageFecha;
    }

    public String getAgeTiempo() {
        return ageTiempo;
    }

    public void setAgeTiempo(String ageTiempo) {
        this.ageTiempo = ageTiempo;
    }

    public Long getAgeEspacios() {
        return ageEspacios;
    }

    public void setAgeEspacios(Long ageEspacios) {
        this.ageEspacios = ageEspacios;
    }

    public Long getAgeVersion() {
        return ageVersion;
    }

    public void setAgeVersion(Long ageVersion) {
        this.ageVersion = ageVersion;
    }

  
 
}