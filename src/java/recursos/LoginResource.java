/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import clases.BaseDatos;
import clases.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author y9d1ru
 */
@Path("login")
public class LoginResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }

    /**
     * Retrieves representation of an instance of recursos.LoginResource
     * @return an instance of java.lang.String
     */

    /**
     * PUT method for updating or creating an instance of LoginResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public String putXml(Usuario usuario) {
        BaseDatos bd = new BaseDatos();
        String validado = bd.loginUsuario(usuario.getNombre());
        if(validado.equals(usuario.getPassword())){
            try {
                System.out.println("entra****************************************************************");
                Algorithm algorithmHS = Algorithm.HMAC256("secret");
                String token = JWT.create().withClaim("nombre", usuario.getNombre()).sign(algorithmHS);
                int idUsuario = bd.getUsuarioID(usuario);
                bd.updateToken(token, idUsuario);
                return token;
            } catch (IllegalArgumentException ex) {
                System.out.println("error1*****************************************************************");
                Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                System.out.println("error2*****************************************************************");
                Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
