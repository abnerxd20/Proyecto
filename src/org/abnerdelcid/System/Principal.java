package org.abnerdelcid.System;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.abnerdelcid.controller.MenuClientesController;
import org.abnerdelcid.controller.MenuPrincipalController;
import org.abnerdelcid.controller.MenuProveedoresController;
import org.abnerdelcid.controller.VistaprogramadorController;

/**
 *
 * 
 */
public class Principal extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/abnerdelcid/view/";
    
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Super Kinal");
       //  Parent root = FXMLLoader.load(getClass().getResource("/org/cristianluna/view/MenuPrincipalView.fxml"));
        //Scene escena = new Scene(root);
        //escenarioPrincipal.setScene(escena);
        menuPrincipalView();
        escenarioPrincipal.show();
        
    }
    
    public Initializable cambiarEscena(String fxmlName, int width, int heigth)throws Exception{
        Initializable resultado = null;
        FXMLLoader louder = new FXMLLoader();
        
        InputStream file =Principal.class.getResourceAsStream(URLVIEW + fxmlName);
        louder.setBuilderFactory(new JavaFXBuilderFactory());
        louder.setLocation(Principal.class.getResource(URLVIEW + fxmlName));
        escena = new Scene((AnchorPane)louder.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)louder.getController();
        return resultado;
    }
    public void menuPrincipalView(){
        try{
            
          MenuPrincipalController menuPrincipalView = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",866,489);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
           
        }
    }
    
    public void menuClientesView(){
        try{

          MenuClientesController menuClientesView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml",866,489);
            menuClientesView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();

        }
    }
    
        public void vistaProgramador(){
        try{

         VistaprogramadorController vistaProgramador = (VistaprogramadorController)cambiarEscena("VistaProgramador.fxml",866,489);
            vistaProgramador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();

        }
    }
        public void MenuProveedores(){
        try{

         MenuProveedoresController MenuProveedores = (MenuProveedoresController)cambiarEscena("VistaProveedores.fxml",866,489);
            MenuProveedores.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();

        }
    }       
    public static void main(String[] args) {
        launch(args);
    }
}
