/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author y9d1ru
 */
public class NuevoXSD {
    public static void nuevo(){
        
            FileWriter xsd = null;
            PrintWriter pw = null;
        try {   
            xsd = new FileWriter("validar.xsd");
            pw = new PrintWriter(xsd);
            pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
"<xs:schema \n" +
"    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"    <xs:simpleType name=\"emailAddress\">\n" +
"        <xs:restriction base=\"xs:token\">\n" +
"            <xs:maxLength value=\"254\"/>\n" +
"            <xs:pattern value=\"[_\\-a-zA-Z0-9\\.\\+]+@[a-zAZ0-9](\\.?[\\-a-zA-Z0-9]*[a-zA-Z0-9])*\"/>\n" +
"        </xs:restriction>\n" +
"    </xs:simpleType>\n" +
"    <xs:complexType name=\"Persona\">\n" +
"        <xs:sequence>\n" +
"            <xs:element name=\"name\" type=\"xs:string\"/>\n" +
"            <xs:element name=\"email\" type=\"emailAddress\"/>\n" +
"            <xs:element name=\"telephone\" type=\"xs:string\"/>\n" +
"            <xs:any namespace=\"##any\" minOccurs=\"0\" maxOccurs=\"unbounded\" processContents=\"lax\"/>\n" +
"        </xs:sequence>\n" +
"    </xs:complexType>\n" +
"    <xs:element name=\"Agenda\">\n" +
"        <xs:complexType>\n" +
"            <xs:sequence>\n" +
"                <xs:element name=\"Persona\" type=\"Persona\" minOccurs=\"0\" maxOccurs=\"unbounded\"/>\n" +
"            </xs:sequence>\n" +
"        </xs:complexType>\n" +
"    </xs:element>\n" +
"    <xs:element name=\"Persona\" type=\"Persona\"/>\n" +
"</xs:schema>");
        } catch (IOException ex) {
            Logger.getLogger(NuevoXSD.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (xsd != null) {
                    xsd.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }
}
