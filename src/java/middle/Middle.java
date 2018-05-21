/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author y9d1ru
 */
@Provider
@Comprobador
public class Middle implements ContainerRequestFilter, ContainerResponseFilter {

 
    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            if (!comprobarToken(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION))) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (NullPointerException ex) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
    
   @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    }
    
    boolean comprobarToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
        return true;
    }
    
}
