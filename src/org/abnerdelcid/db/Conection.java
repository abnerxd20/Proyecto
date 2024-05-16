
package org.abnerdelcid.db;

import java.sql.Connection;
import java.sql.DriverManager;


import java.sql.SQLException;


public class Conection {
    private Connection conexion;
    private static Conection instancia;

    public Conection() {
        
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver"). newInstance();
            conexion =DriverManager.getConnection("jdbc:mysql://localhost:3306/DBKinalExpress?useSSL=false", "2023336_IN5BV*","abc123!!");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static Conection getInstance(){
        
        if(instancia == null)
            instancia = new Conection();
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public static Conection getInstancia() {
        return instancia;
    }

    public static void setInstancia(Conection instancia) {
        Conection.instancia = instancia;
    }

    
    
    
}
