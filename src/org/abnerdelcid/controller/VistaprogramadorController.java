
package org.abnerdelcid.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.abnerdelcid.System.Principal;

public class VistaprogramadorController implements Initializable {
    @FXML Button btnRegresar;
    private Principal escenarioPrincipal; 

    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    @FXML
    public void clicRegresar (ActionEvent event){
         if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
    }
    }
    
}
