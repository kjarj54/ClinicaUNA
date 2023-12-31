package cr.ac.una.wsclinicauna.service;

import cr.ac.una.wsclinicauna.model.CliMedico;
import cr.ac.una.wsclinicauna.model.CliMedicoDto;
import cr.ac.una.wsclinicauna.model.CliParametrosDto;
import cr.ac.una.wsclinicauna.model.CliUsuario;
import cr.ac.una.wsclinicauna.model.CliUsuarioDto;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import cr.ac.una.wsclinicauna.util.Respuesta;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author ArauzKJ
 */
@Stateless
@LocalBean
public class CliUsuarioService {

    private static final Logger LOG = Logger.getLogger(CliUsuarioService.class.getName());
    @PersistenceContext(unitName = "WsClinicaUNAPU")
    private EntityManager em;

    public Respuesta validarUsuario(String usuario, String clave) {
        try {
            Query qryActividad = em.createNamedQuery("CliUsuario.findByUsuarioClave", CliUsuario.class);
            qryActividad.setParameter("usuUsuario", usuario);
            qryActividad.setParameter("usuClave", clave);
            CliUsuario cliUsuario = (CliUsuario) qryActividad.getSingleResult();
            CliUsuarioDto cliUsuarioDto = new CliUsuarioDto(cliUsuario);

            for (CliMedico cliMedico : cliUsuario.getCliMedicoList()) {
                cliUsuarioDto.getCliMedicoList().add(new CliMedicoDto(cliMedico));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", cliUsuarioDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.noUserExist", "validarUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorQueryUser", "validarUsuario NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorQueryUser", "validarUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuario(Long usuId) {
        try {
            Query qryUsuario = em.createNamedQuery("CliUsuario.findByUsuId", CliUsuario.class);
            qryUsuario.setParameter("usuId", usuId);
            CliUsuario cliUsuario = (CliUsuario) qryUsuario.getSingleResult();

            CliUsuarioDto cliUsuarioDto = new CliUsuarioDto(cliUsuario);

            for (CliMedico cliMedico : cliUsuario.getCliMedicoList()) {
                cliUsuarioDto.getCliMedicoList().add(new CliMedicoDto(cliMedico));
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", cliUsuarioDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.noUserCodeExist", "getUsuario NoResultException");
        } catch (NonUniqueResultException ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorQueryGetUser", "getUsuario NonUniqueResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorQueryGetUser", "getUsuario " + ex.getMessage());
        }
    }

    public Respuesta getUsuarios() {
        try {
            Query qryUsuario = em.createNamedQuery("CliUsuario.findAll", CliUsuario.class);
            List<CliUsuario> cliUsuarios = qryUsuario.getResultList();
            List<CliUsuarioDto> cliUsuarioDtos = new ArrayList<>();
            for (CliUsuario cliUsuario : cliUsuarios) {
                CliUsuarioDto cliUsuarioDto = new CliUsuarioDto(cliUsuario);

                for (CliMedico cliMedico : cliUsuario.getCliMedicoList()) {
                    cliUsuarioDto.getCliMedicoList().add(new CliMedicoDto(cliMedico));
                }

                cliUsuarioDtos.add(cliUsuarioDto);
            }

            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuarios", cliUsuarioDtos);

        } catch (NoResultException ex) {
            return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.noUsersCodeExist", "getUsuarios NoResultException");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al consultar los usuarios.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorQueryGetUsers", "getUsuarios " + ex.getMessage());
        }
    }

    public Respuesta guardarUsuario(CliUsuarioDto cliUsuarioDto) {
        try {
            CliUsuario cliUsuario;
            if (cliUsuarioDto.getUsuId() != null && cliUsuarioDto.getUsuId() > 0) {
                cliUsuario = em.find(CliUsuario.class, cliUsuarioDto.getUsuId());
                if (cliUsuario == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.noFoundUserMod", "guardarUsuario NoResultException");
                }
                cliUsuario.actualizar(cliUsuarioDto);

                for (CliMedicoDto cliMedicoDto : cliUsuarioDto.getCliMedicoListEliminados()) {
                    cliUsuario.getCliMedicoList().remove(new CliMedico(cliMedicoDto.getMedId()));
                }

                if (!cliUsuarioDto.getCliMedicoList().isEmpty()) {
                    for (CliMedicoDto cliMedicoDto : cliUsuarioDto.getCliMedicoList()) {
                        if (cliMedicoDto.getModificado()) {
                            CliMedico cliMedico = em.find(CliMedico.class, cliMedicoDto.getMedId());
                            cliMedico.setUsuId(cliUsuario);
                            cliUsuario.getCliMedicoList().add(cliMedico);
                        }
                    }
                }

                cliUsuario = em.merge(cliUsuario);
            } else {
                cliUsuario = new CliUsuario(cliUsuarioDto);
                em.persist(cliUsuario);
            }
            em.flush();
            CliUsuarioDto cliUsuarioDtoR = new CliUsuarioDto(cliUsuario);

            for (CliMedico cliMedico : cliUsuario.getCliMedicoList()) {
                cliUsuarioDtoR.getCliMedicoList().add(new CliMedicoDto(cliMedico));
            }
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "Usuario", cliUsuarioDtoR);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorSavingUser", "guardarUsuario " + ex.getMessage());
        }
    }

    public Respuesta eliminarUsuario(Long id) {
        try {
            CliUsuario cliUsuario;
            if (id != null && id > 0) {
                cliUsuario = em.find(CliUsuario.class, id);
                if (cliUsuario == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.userDeleteNotFound", "eliminarUsuario NoResultException");
                }
                em.remove(cliUsuario);
            } else {
                return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.loadUserDelete", "eliminarUsuario NoResultException");
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorDeleteUserRelations", "eliminarUsuario " + ex.getMessage());
            }
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el empleado.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.deleteUserError", "eliminarUsuario " + ex.getMessage());
        }
    }

    public Respuesta activacionCuenta(Long usuId) {
        try {
            Query qryActividad = em.createNamedQuery("CliUsuario.findByUsuId", CliUsuario.class);
            qryActividad.setParameter("usuId", usuId);
            CliUsuarioDto cliUsuarioDto = new CliUsuarioDto((CliUsuario) qryActividad.getSingleResult());
            cliUsuarioDto.setUsuActivo("A");
            CliUsuario cliUsuario;
            if (cliUsuarioDto.getUsuId() != null && cliUsuarioDto.getUsuId() > 0) {
                cliUsuario = em.find(CliUsuario.class, cliUsuarioDto.getUsuId());
                if (cliUsuario == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.noFoundUserMod", "activacionCuenta NoResultException");
                }
                cliUsuario.actualizar(cliUsuarioDto);
                cliUsuario = em.merge(cliUsuario);
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorSavingUser", "activacionCuenta " + ex.getMessage());
        }
    }

    public Respuesta recuperarClave(String usuCorreo, CliParametrosDto parametrosDto) {
        try {
            Query qryActividad = em.createNamedQuery("CliUsuario.findByUsuCorreo", CliUsuario.class);
            qryActividad.setParameter("usuCorreo", usuCorreo);
            CliUsuarioDto cliUsuarioDto = new CliUsuarioDto((CliUsuario) qryActividad.getSingleResult());
            recuClave(cliUsuarioDto, parametrosDto);
            CliUsuario cliUsuario;
            if (cliUsuarioDto.getUsuId() != null && cliUsuarioDto.getUsuId() > 0) {
                cliUsuario = em.find(CliUsuario.class, cliUsuarioDto.getUsuId());
                if (cliUsuario == null) {
                    return new Respuesta(false, CodigoRespuesta.ERROR_NOENCONTRADO, "key.noFoundUserMod", "recuperarClave NoResultException");
                }
                cliUsuario.actualizar(cliUsuarioDto);
                cliUsuario = em.merge(cliUsuario);
            }
            em.flush();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el usuario.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.errorSavingUser", "recuperarClave " + ex.getMessage());
        }
    }

    public Respuesta correoActivacion(CliUsuarioDto cliUsuarioDto, CliParametrosDto cliParametrosDto) {
        try {
            //setea las propiedades del smtp para poder enviar los emails
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.outlook.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            //proporciona el correo y contrasena del correo con el que va a ser enviado
            String correoRemitente = cliParametrosDto.getParEmail();//"CineUna123@outlook.com";
            String passwordRemitente = cliParametrosDto.getParClave();//"cine1234";
            String correoReceptor = cliUsuarioDto.getUsuCorreo();
            String asunto = "ClinicaUNA";

            //Mensaje que va a ser enviado
            String mensaje = mensajeEmail(cliUsuarioDto, cliParametrosDto.getParHtml(), cliParametrosDto.getParLogo(), cliParametrosDto.getParNombre());

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje, "ISO-8859-1", "html");

            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el cliente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.saveClient", "guardarCliente " + ex.getMessage());
        }
    }

    private static String generateRandomPassword(int len) {
        // Rango ASCII – alfanumérico (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // cada iteración del bucle elige aleatoriamente un carácter del dado
        // rango ASCII y lo agrega a la instancia `StringBuilder`
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    private Respuesta recuClave(CliUsuarioDto cliUsuarioDto, CliParametrosDto cliParametrosDto) {
        int len = 8;
        //System.out.println(generateRandomPassword(len));

        try {
            //setea las propiedades del smtp para poder enviar los emails
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.outlook.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            //Llamada a los parametros
            //proporciona el correo y contrasena del correo con el que va a ser enviado
            String correoRemitente = cliParametrosDto.getParEmail();//"CineUna123@outlook.com";
            String passwordRemitente = cliParametrosDto.getParClave();//"cine1234";
            //Optine el correo al cual va a ser enviado el mensaje
            String correoReceptor = cliUsuarioDto.getUsuCorreo();
            String asunto = "ClinicaUNA";

            //Llama al metodo de creacion de contrasena y se la manda a la persona y luego se la setea para que la cambie
            String claveRestaurada = generateRandomPassword(len);

            String mensaje = mensajeClave(cliParametrosDto.getParHtml(), cliParametrosDto.getParLogo(), cliParametrosDto.getParNombre(), claveRestaurada);

            //Envio del mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje, "ISO-8859-1", "html");

            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();

            cliUsuarioDto.setUsuClave(claveRestaurada);
            cliUsuarioDto.setUsuTempclave(claveRestaurada);
            return new Respuesta(true, CodigoRespuesta.CORRECTO, "", "", "");

        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Ocurrio un error al guardar el cliente.", ex);
            return new Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "key.saveClient", "recuClave " + ex.getMessage());
        }
    }

    private String obtenerIp() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }

    private String mensajeClave(byte[] html, byte[] logo, String nombre, String claveRestaurada) throws IOException {

        String base64Image = convertirABase64(logo);
        String mensaje = convertirBytesAHTML(html);
        String recuperacionMensaje = "Hola por parte de " + nombre + " se generar una nueva clave! La clave nueva es: " + claveRestaurada + "  Atte: " + nombre;

        mensaje = mensaje.replace("{Insertar nombre de la empresa}", nombre);

        mensaje = mensaje.replace("{Contenido que se le vaya a enviar}", recuperacionMensaje);

        mensaje = mensaje.replace("{imagen}", "data:image/png;base64," + base64Image);

        return mensaje;
    }

    private static String convertirBytesAHTML(byte[] bytes) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String html = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        return html;
    }

    private static String convertirABase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private String mensajeEmail(CliUsuarioDto cliUsuarioDto, byte[] html, byte[] logo, String nombre) throws UnknownHostException, IOException {

        String base64Image = convertirABase64(logo);
        String mensaje = convertirBytesAHTML(html);
        String activacionMensaje = "<p style=\"font-size: 14px; line-height: 180%;\"><span style=\"font-size: 18px; line-height: 32.4px; color: #000000;\">"
                + "<span style=\"font-size: 18px; line-height: 32.4px; color: #000000;\">"
                + "<span style=\"line-height: 32.4px; font-family: Montserrat, sans-serif; font-size: 18px;\">"
                + "Presione el link para activar su cuenta: "
                + "<a href=\"http://" + obtenerIp() + ":8080/WsClinicaUNA/ws/CliUsuarioController/activacion/" + cliUsuarioDto.getUsuId() + "\">"
                + "http://" + obtenerIp() + ":8080/WsClinicaUNA/ws/CliUsuarioController/activacion/" + cliUsuarioDto.getUsuId()
                + "</a>"
                + "</span>"
                + "</span>"
                + "</span>"
                + "</p>";
        mensaje = mensaje.replace("{Insertar nombre de la empresa}", nombre);
        mensaje = mensaje.replace("{Contenido que se le vaya a enviar}", activacionMensaje);
        mensaje = mensaje.replace("{imagen}", "data:image/png;base64," + base64Image);

        return mensaje;
    }
}
