/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author y9d1ru
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(middle.Middle.class);
        resources.add(recursos.AgendaResource.class);
        resources.add(recursos.ContactoResource.class);
        resources.add(recursos.LoginResource.class);
        resources.add(recursos.PersonaResource.class);
        resources.add(recursos.RegistoResource.class);
        resources.add(recursos.ValidarAgendaResource.class);
        resources.add(recursos.ValidarPersonaResource.class);
    }
    
}
