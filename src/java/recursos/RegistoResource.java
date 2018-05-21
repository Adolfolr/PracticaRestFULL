/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import clases.BaseDatos;
import clases.Usuario;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author y9d1ru
 */
@Path("registro")
public class RegistoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegistoResource
     */
    public RegistoResource() {
    }

    /**
     * Retrieves representation of an instance of recursos.RegistoResource
     * @return an instance of java.lang.String
     */


    /**
     * PUT method for updating or creating an instance of RegistoResource
     * @param content representation for the resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void postXml(Usuario usuario) {
        BaseDatos bd = new BaseDatos();
        bd.insertarUsuario(usuario);
        int id = bd.getUsuarioID(usuario);
        bd.crearAgenda(usuario.getNombre(), bd.getUsuarioID(usuario));
        //acceso a la base de datos, recoger usuario
    }
}
