package cr.ac.una.clinicauna.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ANTHONY
 */
public class CliAntecedenteDto {

    public SimpleStringProperty antId;
    public SimpleStringProperty antDescripcion;
    public SimpleStringProperty antTipo;
    public SimpleStringProperty antParentesco;
    public CliExpedienteDto cliExpedienteDto;
    private Long antVersion;
    private Boolean modificado;

    public CliAntecedenteDto() {
        this.antId = new SimpleStringProperty();
        this.antDescripcion = new SimpleStringProperty();
        this.antTipo = new SimpleStringProperty();
        this.antParentesco = new SimpleStringProperty();
        this.modificado = false;
    }

    public Long getAntId() {
        if (this.antId.get() != null && !this.antId.get().isEmpty()) {
            return Long.valueOf(this.antId.get());
        } else {
            return null;
        }
    }

    public void setAntId(Long antId) {
        this.antId.set(antId.toString());
    }

    public String getAntDescripcion() {
        return antDescripcion.get();
    }

    public void setAntDescripcion(String antDescripcion) {
        this.antDescripcion.set(antDescripcion);
    }

    public String getAntTipo() {
        return antTipo.get();
    }

    public void setAntTipo(String antTipo) {
        this.antTipo.set(antTipo);
    }

    public String getAntParentesco() {
        return antParentesco.get();
    }

    public void setAntParentesco(String antParentesco) {
        this.antParentesco.set(antParentesco);
    }

    public CliExpedienteDto getCliExpedienteDto() {
        return cliExpedienteDto;
    }

    public void setCliExpedienteDto(CliExpedienteDto cliExpedienteDto) {
        this.cliExpedienteDto = cliExpedienteDto;
    }

    public Long getAntVersion() {
        return antVersion;
    }

    public void setAntVersion(Long antVersion) {
        this.antVersion = antVersion;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
}
