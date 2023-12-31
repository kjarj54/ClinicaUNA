package cr.ac.una.clinicauna.controller;

import com.jfoenix.controls.JFXDatePicker;
import cr.ac.una.clinicauna.model.CliAgendaDto;
import cr.ac.una.clinicauna.model.CliCitaDto;
import cr.ac.una.clinicauna.model.CliMedicoDto;
import cr.ac.una.clinicauna.model.CliPacienteDto;
import cr.ac.una.clinicauna.model.CliUsuarioDto;
import cr.ac.una.clinicauna.service.CliAgendaService;
import cr.ac.una.clinicauna.service.CliMedicoService;
import cr.ac.una.clinicauna.util.AppContext;
import cr.ac.una.clinicauna.util.FlowController;
import cr.ac.una.clinicauna.util.Mensaje;
import cr.ac.una.clinicauna.util.Respuesta;
import cr.ac.una.clinicauna.util.SoundUtil;
import cr.ac.una.clinicauna.util.Utilidades;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Luvara
 */
public class P12_AtencionCitasViewController extends Controller implements Initializable {

    @FXML
    private MFXButton btnSalir;
    @FXML
    private JFXDatePicker dtpFecha;
    @FXML
    private TableView<CliCitaDto> tbvCitas;
    @FXML
    private MFXButton btnIrExpediente;
    @FXML
    private Label lblUserName;
    @FXML
    private AnchorPane root;

    CliUsuarioDto usuarioDto;
    CliMedicoDto medicoDto;
    CliAgendaDto agendaDto;
    CliPacienteDto pacienteDto;
    Object resultado;
    private ObservableList<CliCitaDto> listaCitas = FXCollections.observableArrayList();
    List<CliMedicoDto> listaMedicos = new ArrayList<>();
    List<CliAgendaDto> listaAgendas = new ArrayList<>();
    ResourceBundle resourceBundle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resourceBundle = FlowController.getInstance().getIdioma();
        Utilidades.ajustarAnchorVentana(root);
        usuarioDto = (CliUsuarioDto) AppContext.getInstance().get("Usuario");
        lblUserName.setText(usuarioDto.nombreDosApellidos());
        fillTableView();
        dtpFecha.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (usuarioDto.getUsuTipousuario().equals("M")) {
                    cargarCitasMedico();
                } else {
                    cargarCitasAdmin();
                }
            }
        });
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void onActionBtnIrExpediente(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        resultado = tbvCitas.getSelectionModel().getSelectedItem();
        if (resultado != null) {
            CliCitaDto citaDto = (CliCitaDto) resultado;
            pacienteDto = citaDto.getCliPacienteDto();

            P13_ExpedienteViewController expedienteController = (P13_ExpedienteViewController) FlowController.getInstance().getController("P13_ExpedienteView");
            expedienteController.cargarPaciente(pacienteDto, usuarioDto, citaDto);
            FlowController.getInstance().goViewInWindow("P13_ExpedienteView", true);
        }
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        SoundUtil.mouseEnterSound();
        FlowController.getInstance().goView("P06_MenuPrincipalView");
    }

    public void fillTableView() {
        tbvCitas.getItems().clear();

        TableColumn<CliCitaDto, String> tbcFecha = new TableColumn<>(resourceBundle.getString("key.date"));
        tbcFecha.setSortable(false);
        tbcFecha.setPrefWidth(150);
        tbcFecha.setCellValueFactory(cd -> {
            LocalDateTime fecha = cd.getValue().getCitFechaHora();
            String fechaString = fecha.getDayOfMonth() + "/" + fecha.getMonthValue() + "/" + fecha.getYear();
            return new SimpleStringProperty(fechaString);
        });

        TableColumn<CliCitaDto, String> tbcNombre = new TableColumn<>(resourceBundle.getString("key.namePacient"));
        tbcNombre.setSortable(false);
        tbcNombre.setPrefWidth(200);
        tbcNombre.setCellValueFactory(cd -> {
            String nombrePac = cd.getValue().nombrePacienteCompleto();
            return new SimpleStringProperty(nombrePac);
        });

        TableColumn<CliCitaDto, String> tbcMotivo = new TableColumn<>(resourceBundle.getString("key.reason"));
        tbcMotivo.setSortable(false);
        tbcMotivo.setPrefWidth(150);
        tbcMotivo.setCellValueFactory(cd -> {
            String motivo = (cd.getValue().getCitMotivo() == null) ? "No indica" : cd.getValue().getCitMotivo();

            return new SimpleStringProperty(motivo);
        });

        TableColumn<CliCitaDto, String> tbcHora = new TableColumn<>(resourceBundle.getString("key.attenHora"));
        tbcHora.setSortable(false);
        tbcHora.setPrefWidth(150);
        tbcHora.setCellValueFactory(cd -> {
            LocalDateTime fecha = cd.getValue().getCitFechaHora();
            int hora = fecha.getHour();
            int minuto = fecha.getMinute();

            String horaFormateada;

            String aux = (hora < 12) ? " am." : " pm.";
            String minutes = (minuto == 0) ? ":0" : ":";
            int hora12 = (hora <= 12) ? hora : hora - 12;

            horaFormateada = (hora12 < 10) ? "0" : "";
            horaFormateada += hora12 + minutes + minuto + aux;

            return new SimpleStringProperty(horaFormateada);
        });

        TableColumn<CliCitaDto, String> tbcDuracion = new TableColumn<>(resourceBundle.getString("key.attenDuracion"));
        tbcDuracion.setSortable(false);
        tbcDuracion.setPrefWidth(150);
        tbcDuracion.setCellValueFactory(cd -> {
            int duracion = cd.getValue().getCliCantespacios().intValue();

            if (usuarioDto.getUsuTipousuario().equals("M")) {
                duracion *= Integer.parseInt(agendaDto.getAgeTiempo());
            } else {
                duracion = 0;
            }

            String duraString = duracion + " minutos";

            return new SimpleStringProperty(duraString);
        });

        tbvCitas.getColumns().addAll(tbcFecha, tbcHora, tbcNombre, tbcMotivo, tbcDuracion);
        tbvCitas.refresh();
    }

    private void cargarCitasMedico() {

        CliMedicoService medicoService = new CliMedicoService();
        Respuesta resMedico = medicoService.getMedicos(usuarioDto.getUsuId());

        if (resMedico.getEstado()) {
            listaMedicos = (List<CliMedicoDto>) resMedico.getResultado("Medicos");
            medicoDto = listaMedicos.get(0);

            CliAgendaService service = new CliAgendaService();
            Respuesta respuesta = service.getAgenda(medicoDto, dtpFecha.getValue());

            if (respuesta.getEstado()) {
                agendaDto = (CliAgendaDto) respuesta.getResultado("Agenda"); // agendaDto tiene la agenda seleccionada, dentro tiene las citas y el paciente de cada cita
                tbvCitas.getItems().clear();
                listaCitas.clear();
                listaCitas.addAll(agendaDto.getCliCitaList());
                tbvCitas.setItems(listaCitas);
                tbvCitas.refresh();
            } else {
                new Mensaje().showModali18n(Alert.AlertType.ERROR, "key.loadAgenda", getStage(), respuesta.getMensaje());
            }
        } else {
            new Mensaje().showModali18n(Alert.AlertType.ERROR, "key.loadDoctor", getStage(), resMedico.getMensaje());
        }
    }

    private void cargarCitasAdmin() {
        CliAgendaService service = new CliAgendaService();
        Respuesta respuesta = service.getAgendas(dtpFecha.getValue());

        if (respuesta.getEstado()) {
            listaAgendas = (List<CliAgendaDto>) respuesta.getResultado("Agenda");
            //agendaDto = (CliAgendaDto) respuesta.getResultado("Agenda");
            
            tbvCitas.getItems().clear();
            listaCitas.clear();
            listaCitas.addAll((List<CliCitaDto>) respuesta.getResultado("Agenda"));
            tbvCitas.setItems(listaCitas);
            tbvCitas.refresh();
        } else {
            new Mensaje().showModali18n(Alert.AlertType.ERROR, "key.loadAgenda", getStage(), respuesta.getMensaje());
        }
    }

}
