/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos;

import clases.Agenda;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
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
@Path("validarAgenda")
public class ValidarAgendaResource {

    @Context
    private UriInfo context;
    
    public ValidarAgendaResource() {
    }
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public String postXML(Agenda a) {
        File schemaFile = new File("validador.xsd");
        if (!schemaFile.exists()) {
            NuevoXSD.nuevo();
        }
        ImportarExportar ie = new ImportarExportar("Agenda.xml");
//        System.out.println("aqui imprimo la primera persona1");
//        System.out.println(a.getPersonas().get(0).getNombre());
//        System.out.println(a.getPersonas().get(0).getEmail());
//        System.out.println(a.getPersonas().get(0).getTelefono());


        File f = ie.guardarAgenda(a);
//        System.out.println("aqui imprimo la primera persona2");
//        System.out.println(a.getPersonas().get(0).getNombre());
        Source xmlFile = new StreamSource(f);   
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try{
            Schema schema = schemaFactory.newSchema(schemaFile);
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
            return "--------\n La agenda es válida \n--------";
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
            return "--------\n La agenda no es válida\n --------";
        } catch (IOException ex) {
            Logger.getLogger(ValidarAgendaResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "false";
    }
}
//    @POST
//    @Consumes(MediaType.APPLICATION_XML)
//    public String postXml(Agenda a){
//        File schemaFile = new File("validador.xsd");
//        if (!schemaFile.exists()) {
//            NuevoXSD.nuevo();
//        }
//        ImportarExportar ie = new ImportarExportar("Agenda.xml");
//        File f = ie.guardarAgenda(a);
//        Source xmlFile = new StreamSource(f);   
//        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        try {
//            Schema schema = schemaFactory.newSchema(schemaFile);
//            javax.xml.validation.Validator validator = schema.newValidator();
//            validator.validate(xmlFile);
//            System.out.println(xmlFile.getSystemId() + " is valid");
//            return "true";
//        } catch (SAXException e) {
//            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
//            return "false";
//        } catch (IOException ex) {
//            Logger.getLogger(ValidarAgendaResource.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "false";
//    }
//}
