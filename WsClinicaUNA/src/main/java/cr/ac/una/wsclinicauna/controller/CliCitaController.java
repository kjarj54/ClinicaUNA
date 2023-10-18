/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.wsclinicauna.controller;

import cr.ac.una.wsclinicauna.model.CliCitaDto;
import cr.ac.una.wsclinicauna.service.CliCitaService;
import cr.ac.una.wsclinicauna.util.CodigoRespuesta;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ArauzKJ
 */
@Path("/CliCitaController")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Cita", description = "Operaciones sobre cita")
public class CliCitaController {
    @EJB
    CliCitaService cliCitaService;
    
    @GET
    @Path("/cita/{id}")
    public Response getCita(@PathParam("id") Long id) {
        try {
            
            return Response.ok().build();//TODO
        } catch (Exception ex) {
            Logger.getLogger(CliCitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo el cita").build();//TODO
        }
    }

    @GET
    @Path("/citas")
    public Response getCitas() {
        try {
            
            return Response.ok().build();
        } catch (Exception ex) {
            Logger.getLogger(CliCitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error obteniendo los cita").build();//TODO
        }
    }

    //TODO
    @POST
    @Path("/cita")
    public Response guardarCita(CliCitaDto cliCitaDto) {
        try {
            
            return Response.ok().build();//TODO
        } catch (Exception ex) {
            Logger.getLogger(CliCitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error guardando el cita").build();//TODO
        }
    }

    @DELETE
    @Path("/eliminarCita/{id}")
    public Response eliminarCita(@PathParam("id") Long id) {
        try {
            return Response.ok().build();//TODO
        } catch (Exception ex) {
            Logger.getLogger(CliCitaController.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(CodigoRespuesta.ERROR_INTERNO.getValue()).entity("Error eliminando el cita").build();//TODO
        }
    }
}