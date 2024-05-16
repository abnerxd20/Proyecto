package org.abnerdelcid.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.abnerdelcid.System.Principal;
import org.abnerdelcid.controller.MenuClientesController;


public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML MenuItem btnMenuclientes;
    @FXML MenuItem btnVistaProgramador;
    @FXML MenuItem btnVistaProveedores;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void clicClientes (ActionEvent event){
             if (event.getSource() == btnMenuclientes){
                 
            escenarioPrincipal.menuClientesView();
           
        }
    }
        public void clicProgramador (ActionEvent event){
             if (event.getSource() == btnVistaProgramador){
            escenarioPrincipal.vistaProgramador();
        }
    }
    
            public void clicProveedores (ActionEvent event){
             if (event.getSource() == btnVistaProveedores){
            escenarioPrincipal.MenuProveedores();
        }
    }

}