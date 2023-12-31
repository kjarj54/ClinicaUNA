package cr.ac.una.wsclinicauna.model;

/**
 *
 * @author ArauzKJ
 */
public class CliParametrosDto {

    private Long parId;
    private String parNombre;
    private String parEmail;
    private String parClave;
    private byte[] parHtml;
    private byte[] parLogo;
    private String parDescripcion;
    private Long parVersion;
    private Boolean modificado;

    public CliParametrosDto() {
        this.modificado = false;
    }

    public CliParametrosDto(CliParametros cliParametros) {
        this();
        this.parId = cliParametros.getParId();
        this.parNombre = cliParametros.getParNombre();
        this.parEmail = cliParametros.getParEmail();
        this.parClave = cliParametros.getParClave();
        this.parHtml = (byte[]) cliParametros.getParHtml();
        this.parLogo = (byte[]) cliParametros.getParFoto();
        this.parDescripcion = cliParametros.getParDescripcion();
        this.parVersion = cliParametros.getParVersion();
    }

    public Long getParId() {
        return parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
    }

    public String getParNombre() {
        return parNombre;
    }

    public void setParNombre(String parNombre) {
        this.parNombre = parNombre;
    }

    public String getParEmail() {
        return parEmail;
    }

    public void setParEmail(String parEmail) {
        this.parEmail = parEmail;
    }

    public String getParClave() {
        return parClave;
    }

    public void setParClave(String parClave) {
        this.parClave = parClave;
    }

    public byte[] getParHtml() {
        return parHtml;
    }

    public void setParHtml(byte[] parHtml) {
        this.parHtml = parHtml;
    }

    public byte[] getParLogo() {
        return parLogo;
    }

    public void setParLogo(byte[] parLogo) {
        this.parLogo = parLogo;
    }

    public String getParDescripcion() {
        return parDescripcion;
    }

    public void setParDescripcion(String parDescripcion) {
        this.parDescripcion = parDescripcion;
    }

    public Long getParVersion() {
        return parVersion;
    }

    public void setParVersion(Long parVersion) {
        this.parVersion = parVersion;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

}
