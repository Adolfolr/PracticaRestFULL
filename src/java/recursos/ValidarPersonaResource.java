/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import clases.Persona;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import metodos.ImportarExportar;
import metodos.NuevoXSD;
import org.xml.sax.SAXException;

/**
 * REST Web Service
 *
 * @author y9d1ru
 */
@Path("validarPersona")
public class ValidarPersonaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ValidarPersonaResource
     */
    public ValidarPersonaResource() {
    }

    /**
     * Retrieves representation of an instance of recursos.ValidarPersonaResource
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public String postXML(Persona p) {
        File schemaFile = new File("validador.xsd");
        if (!schemaFile.exists()) {
            NuevoXSD.nuevo();
        }
        ImportarExportar i = new ImportarExportar("agenda.xml");
        File f = i.guardarPersona(p);
        Source xmlFile = new StreamSource(f);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
            return "true";
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
            return "false";
        } catch (IOException ex) {
            Logger.getLogger(ValidarAgendaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "false";
    }
}
