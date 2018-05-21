/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import clases.Agenda;
import clases.Persona;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import metodos.ImportarExportar;

/**
 * REST Web Service
 *
 * @author y9d1ru
 */
@Path("persona")
public class PersonaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonaResource
     */
    public PersonaResource() {
    }

    /**
     * Retrieves representation of an instance of recursos.PersonaResource
     * @return an instance of java.lang.String
     */
    //------------------ESTO ES LEGACY; SE PUEDE BORRAR-----------------------
    @GET
    @Path("{nombre}")
    @Produces(MediaType.APPLICATION_XML)
    public Persona getXml(@PathParam("nombre") String nombre) {
        ImportarExportar ie = new ImportarExportar("agenda.xml");
        Agenda a = ie.cargar();
        for(int i=0;i<a.getPersonas().size();i++){
            System.out.println(a.getPersonas().get(i).getNombre());
            if(a.getPersonas().get(i).getNombre().equals(nombre)){
                return a.getPersonas().get(i);
            }
        }
        return null;
    }

    /**
     * PUT method for updating or creating an instance of PersonaResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void postAgenda(Persona p) {
        ImportarExportar ie = new ImportarExportar("agenda.xml");
        Agenda a = ie.cargar();
        a.anadirPersona(p);
        ie.guardarAgenda(a);
    }
}