
package cr.ac.una.wsclinicauna.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ArauzKJ
 */
public class CliAtencionDto {

    private Long ateId;
    private LocalDateTime ateFechahora;
    private String atePresion;
    private String ateFrecuenciacard;
    private String atePeso;
    private String ateTalla;
    private String ateTemperatura;
    private String ateImc;
    private String ateAnotacionenfe;
    private String ateRazonconsulta;
    private String atePlanatencion;
    private String ateObservaciones;
    private String ateTratamiento;
    private Long ateVersion;
    private List<CliExamenDto> cliExamenList;
    private List<CliExamenDto> cliExamenListEliminados;
    private CliExpedienteDto cliExpedienteDto;
    private Boolean modificado;
    private LocalDateTime fecha;

    public CliAtencionDto() {
        this.cliExamenList = new ArrayList<>();
        this.cliExamenListEliminados = new ArrayList<>();
        this.modificado = false;
        this.fecha = LocalDateTime.now();
    }

    public CliAtencionDto(CliAtencion cliAtencion) {
        this();
        this.ateId = cliAtencion.getAteId();
        this.ateFechahora = cliAtencion.getAteFechahora();
        this.atePresion = cliAtencion.getAtePresion();
        this.ateFrecuenciacard = cliAtencion.getAteFrecuenciacard();
        this.atePeso = cliAtencion.getAtePeso();
        this.ateTalla = cliAtencion.getAteTalla();
        this.ateTemperatura = cliAtencion.getAteTemperatura();
        this.ateImc = cliAtencion.getAteImc();
        this.ateAnotacionenfe = cliAtencion.getAteAnotacionenfe();
        this.ateRazonconsulta = cliAtencion.getAteRazonconsulta();
        this.atePlanatencion = cliAtencion.getAtePlanatencion();
        this.ateObservaciones = cliAtencion.getAteObservaciones();
        this.ateTratamiento = cliAtencion.getAteTratamiento();
        this.ateVersion = cliAtencion.getAteVersion();
        this.fecha = LocalDateTime.now();
    }

    public Long getAteId() {
        return ateId;
    }

    public void setAteId(Long ateId) {
        this.ateId = ateId;
    }

    public LocalDateTime getAteFechahora() {
        return ateFechahora;
    }

    public void setAteFechahora(LocalDateTime ateFechahora) {
        this.ateFechahora = ateFechahora;
    }

    public String getAtePresion() {
        return atePresion;
    }

    public void setAtePresion(String atePresion) {
        this.atePresion = atePresion;
    }

    public String getAteFrecuenciacard() {
        return ateFrecuenciacard;
    }

    public void setAteFrecuenciacard(String ateFrecuenciacard) {
        this.ateFrecuenciacard = ateFrecuenciacard;
    }

    public String getAtePeso() {
        return atePeso;
    }

    public void setAtePeso(String atePeso) {
        this.atePeso = atePeso;
    }

    public String getAteTalla() {
        return ateTalla;
    }

    public void setAteTalla(String ateTalla) {
        this.ateTalla = ateTalla;
    }

    public String getAteTemperatura() {
        return ateTemperatura;
    }

    public void setAteTemperatura(String ateTemperatura) {
        this.ateTemperatura = ateTemperatura;
    }

    public String getAteImc() {
        return ateImc;
    }

    public void setAteImc(String ateImc) {
        this.ateImc = ateImc;
    }

    public String getAteAnotacionenfe() {
        return ateAnotacionenfe;
    }

    public void setAteAnotacionenfe(String ateAnotacionenfe) {
        this.ateAnotacionenfe = ateAnotacionenfe;
    }

    public String getAteRazonconsulta() {
        return ateRazonconsulta;
    }

    public void setAteRazonconsulta(String ateRazonconsulta) {
        this.ateRazonconsulta = ateRazonconsulta;
    }

    public String getAtePlanatencion() {
        return atePlanatencion;
    }

    public void setAtePlanatencion(String atePlanatencion) {
        this.atePlanatencion = atePlanatencion;
    }

    public String getAteObservaciones() {
        return ateObservaciones;
    }

    public void setAteObservaciones(String ateObservaciones) {
        this.ateObservaciones = ateObservaciones;
    }

    public String getAteTratamiento() {
        return ateTratamiento;
    }

    public void setAteTratamiento(String ateTratamiento) {
        this.ateTratamiento = ateTratamiento;
    }

    public Long getAteVersion() {
        return ateVersion;
    }

    public void setAteVersion(Long ateVersion) {
        this.ateVersion = ateVersion;
    }

    public List<CliExamenDto> getCliExamenList() {
        return cliExamenList;
    }

    public void setCliExamenList(List<CliExamenDto> cliExamenList) {
        this.cliExamenList = cliExamenList;
    }

    public List<CliExamenDto> getCliExamenListEliminados() {
        return cliExamenListEliminados;
    }

    public void setCliExamenListEliminados(List<CliExamenDto> cliExamenListEliminados) {
        this.cliExamenListEliminados = cliExamenListEliminados;
    }

    public CliExpedienteDto getCliExpedienteDto() {
        return cliExpedienteDto;
    }

    public void setCliExpedienteDto(CliExpedienteDto cliExpedienteDto) {
        this.cliExpedienteDto = cliExpedienteDto;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

}
