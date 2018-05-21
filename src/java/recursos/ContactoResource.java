/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import clases.Agenda;
import clases.BaseDatos;
import clases.Persona;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import middle.Comprobador;

/**
 * REST Web Service
 *
 * @author y9d1ru
 */
@Path("generic")
public class ContactoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ContactoResource
     */
    public ContactoResource() {
    }

    /**
     * Retrieves representation of an instance of recursos.ContactoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Comprobador
    @Produces(MediaType.APPLICATION_XML)
    public Agenda getXml(@QueryParam("nombre") String nombre, @Context HttpHeaders httphead) {
        String token = httphead.getHeaderString("Authorization");
        BaseDatos b = new BaseDatos();
        Agenda a = b.listarPersona(nombre, token);
        return a;
    }

    /**
     * PUT method for updating or creating an instance of ContactoResource
     * @param content representation for the resource
     */
    @POST
    @Comprobador
    @Consumes(MediaType.APPLICATION_XML)
    public void postXml(Persona p, @Context HttpHeaders httphead) {
        String token = httphead.getHeaderString("Authorization");
        BaseDatos b = new BaseDatos();
        b.anadirContacto(p,token);
    }
    
    @PUT
    @Comprobador
    @Path("/{idContacto}")
    @Consumes(MediaType.APPLICATION_XML)
    public void putXML(Persona p, @PathParam("idContacto") String idContacto,@Context HttpHeaders httphead){
        String token = httphead.getHeaderString("Authorization");
        BaseDatos b = new BaseDatos();
        int idCont = Integer.parseInt(idContacto);
        b.actualizarContacto(p, idCont, token);
    }
    
    @DELETE
    @Comprobador
    @Path("/{idContacto}")
    public void deleteXML(@Context HttpHeaders httphead, @PathParam("idContacto")String idContacto){
        String token = httphead.getHeaderString("Authorization");
        BaseDatos b = new BaseDatos();
        int idCont = Integer.parseInt(idContacto);
        b.eliminarContacto(idCont, token);
    }
}
