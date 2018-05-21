/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Statement;
import java.util.ArrayList;
/**
 *
 * @author y9d1ru
 */
public class BaseDatos {

    DataSource dataSource;
    Statement statement;
    ResultSet resultSet;
    Connection c;

    public void init() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("jdbc/agenda");
        } catch (NamingException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Agenda verAgenda(String token){
        try {
            init();
            String query1 = "select idagenda from agenda where idUsuario=(SELECT idUsuarios from usuarios where token='"+token+"')";
            c = dataSource.getConnection();
            statement = c.createStatement();
            resultSet =statement.executeQuery(query1);
            int idAgenda = 0;
            while(resultSet.next()){
                idAgenda=resultSet.getInt("idagenda");
            }
            resultSet.close();
            statement.close();
            c.close();
            String query2 = "Select * from contactos where idAgenda="+idAgenda;
            c = dataSource.getConnection();
            statement = c.createStatement();
            resultSet = statement.executeQuery(query2);
//            ResultSetMetaData rsmd = resultSet.getMetaData();
            ArrayList<Persona> personas=new ArrayList<>();
//            System.out.println("buscarColumna");
//            while (resultSet.next()) {
//       for (int i = 1; i <= 4; i++) {
//           if (i > 1) System.out.print(",  ");
//           String columnValue = resultSet.getString(i);
//           System.out.print(columnValue + " " + rsmd.getColumnName(i));
//       }
//       System.out.println("");
//   }
            while(resultSet.next()){
                String nombre = resultSet.getString("nombre");
                personas.add(new Persona(nombre, resultSet.getString("mail") ,resultSet.getString("telefono")));
            }
            Agenda a = new Agenda(personas);
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            cerrarConexiones();
        }
        return null;
    }

    public void insertarUsuario(Usuario u) {
        init();
        try {
            String sentencia = "insert into usuarios (nombre,password) values('" + u.getNombre() + "','" + u.getPassword() + "')";
            c = dataSource.getConnection();
            statement = c.createStatement();
            statement.execute(sentencia);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexiones();
        }
    }
    
    public int getUsuarioID(Usuario usuario){
        try {
            init();
            int id = 0;
            c = dataSource.getConnection();
            statement = c.createStatement();
            String query = "select idUsuarios from Usuarios where nombre ='"+usuario.getNombre()+"'";
            resultSet = statement.executeQuery(query);
            Agenda agenda = new Agenda();
            while(resultSet.next()){
                id=resultSet.getInt("idUsuarios");
            }
            return id;
    
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public void crearAgenda(String nombreAgenda, int idUsuarios){
        try {
            init();
            String query = "insert into agenda values (null, '"+nombreAgenda+"',"+idUsuarios+")";
            Connection connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute(query);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    
    public String loginUsuario(String nombre){
        try {
            init();
            String sentencia = "select password from usuarios where nombre='"+nombre+"'";
            c = dataSource.getConnection(); 
            statement = c.createStatement();
            ResultSet r = statement.executeQuery(sentencia);
            while(r.next()){
                return r.getString("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }return null;
    }
    
    public void updateToken(String token, int idUsuario){
    try {
            String query = "update Usuarios set token='"+token+"'where idUsuarios='"+idUsuario+"'";
            c = dataSource.getConnection();
            statement = c.createStatement();
            statement.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    
    public Agenda listarPersona(String nombre,String token) {
        init();
        try {
            String query1 = "select idagenda from agenda where idUsuario=(SELECT idUsuarios from usuarios where token = '" + token +"')";
            c = dataSource.getConnection();
            statement = c.createStatement();
            resultSet = statement.executeQuery(query1);
            int idAgenda=0;
            while(resultSet.next()){
                idAgenda=resultSet.getInt("idagenda");
            }
            resultSet.close();
            statement.close();
            c.close();
            
            c = dataSource.getConnection();
            statement = c.createStatement();
            String query2 = "select * from contactos where idAgenda="+idAgenda+" and nombre ='"+nombre+"'";
            resultSet=statement.executeQuery(query2);
            ArrayList<Persona> personas = new ArrayList<>();
            while(resultSet.next()){
                personas.add(new Persona(resultSet.getString("nombre"), resultSet.getString("telefono"), resultSet.getString("mail")));
            }
            Agenda a = new Agenda(personas);
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarConexiones();
        }
        return null;
    }
    
            
    
    public void anadirContacto(Persona p, String token){
        init();
        try {
            String query1 = "select idagenda from agenda where idUsuario=(select"
                    + " idUsuarios from usuarios where token='"+token+"')";
            c = dataSource.getConnection();
            statement = c.createStatement();
            resultSet = statement.executeQuery(query1);
            int idAgenda=0;
            while(resultSet.next()){
                idAgenda=resultSet.getInt("idagenda");
            }
            resultSet.close();
            statement.close();
            c.close();
            String query2 = "insert into contactos (nombre,mail,telefono,idAgenda) v"
                    + "alues ('"+p.getNombre()+"'"+ ",'"+p.getEmail()+"','"
                    + ""+p.getTelefono()+"',"+idAgenda+")";
            c = dataSource.getConnection();
            statement = c.createStatement();
            statement.execute(query2);
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            cerrarConexiones();
        }
    }
    
    public void actualizarContacto(Persona p, int idContacto, String token){
        init();
        try {
            String query1="select idagenda from agenda where idUsuario=(select idUsuarios from usuarios where token='"+token+"')";
            
            c = dataSource.getConnection();
            statement = c.createStatement();
            resultSet = statement.executeQuery(query1);
            int idAgenda=0;
            while(resultSet.next()){
                idAgenda=resultSet.getInt("idagenda");
            }
            resultSet.close();
            statement.close();
            c.close();
            String query2 = "update contactos set nombre='"+p.getNombre()+"',telefono='"+p.getTelefono()+
                   "',mail='"+p.getEmail()+"' where idContacto="+idContacto+" and idAgenda="+idAgenda;
            c = dataSource.getConnection();
            statement = c.createStatement();
            statement.execute(query2);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }finally {cerrarConexiones();}
    }
    
    public void eliminarContacto(int idContacto, String token){
        try {
            init();
            String query1="select idagenda from agenda where idUsuario=(select idUsuarios from usuarios where token='"+token+"')";
            c = dataSource.getConnection();
            statement = c.createStatement();
            resultSet = statement.executeQuery(query1);
            int idAgenda=0;
            while(resultSet.next()){
                idAgenda=resultSet.getInt("idagenda");
            }
            resultSet.close();
            statement.close();
            c.close();
            
            String query2 = "delete from contactos where idContacto="+idContacto+" and idAgenda="+idAgenda;
            c = dataSource.getConnection();
            statement = c.createStatement();
            statement.execute(query2);
            
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }finally{cerrarConexiones();}
        
    }
    
    public void cerrarConexiones() {
        if (resultSet != null) {
            try {
                resultSet.close();
           } catch (SQLException ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (c != null) {
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
