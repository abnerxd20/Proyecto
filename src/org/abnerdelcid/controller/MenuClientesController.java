
package org.abnerdelcid.controller;

import java.awt.Image;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.abnerdelcid.System.Principal;
import org.abnerdelcid.bean.Clientes;
import org.abnerdelcid.db.Conection;


public class MenuClientesController implements Initializable {
      
        private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANSELAR,NINGUNO}
        private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        private ObservableList<Clientes> listaClientes;
   
        @FXML Button btnRegresar;
        @FXML private TextField txtDireccionC;
        @FXML private TextField txtCorreoC;
        @FXML private TextField txtCodigoCliente;
        @FXML private TextField txtTelefonoC;
        @FXML private TextField txtNitC;
        @FXML private TextField txtNombreC;
        @FXML private TextField txtApellidoC;
        @FXML private TableView tbClientes;
        
        @FXML private TableColumn colCodigoC;
        @FXML private TableColumn colNombreC;
        @FXML private TableColumn colNitC;
        @FXML private TableColumn colApellidoC;
        @FXML private TableColumn colDireccionC;
        @FXML private TableColumn colTelefonoC;
         @FXML private TableColumn colCorreoC;
        @FXML Button btnAgregar;
        @FXML Button btnEliminar;
        @FXML Button btnEditar;
        @FXML Button btnReporte;
        
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }   
    
    public void cargarDatos(){
       tbClientes.setItems(getClientes()); 
       colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
       colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
       colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
       colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
       colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("direccionCliente"));
       colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
       colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
       
    }
    
    public void seleccionarElemento(){

        txtCodigoCliente.setText(String.valueOf(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));

        txtNombreC.setText(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getNombreCliente());

        txtApellidoC.setText(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());

        txtNitC.setText(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getNITCliente());

        txtTelefonoC.setText(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());

        txtDireccionC.setText(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());

        txtCorreoC.setText(((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getCorreoCliente());

    }
    public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> Lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conection.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                Lista.add(new Clientes (resultado.getInt("codigoCliente"),
                                        resultado.getString("NITCliente"),
                                        resultado.getString("nombreCliente"),
                                        resultado.getString("apellidoCliente"),
                                        resultado.getString("direccionCliente"),
                                        resultado.getString("telefonoCliente"),
                                        resultado.getString("correoCliente")
                ));
                                        
                   
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(Lista);
    }
    
    public void agregar(){
        switch (tipoDeOperaciones) {
            case NINGUNO:
             
                activarControles();
                btnAgregar.setText("Guardar");
                btnReporte.setText("Cacelar");
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
                tipoDeOperaciones = tipoDeOperaciones.ACTUALIZAR;
                limpiarControles();
                break;
            case ACTUALIZAR:
                guardar();
                
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agreegar");
                btnReporte.setText("Reporte");
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperaciones = tipoDeOperaciones.NINGUNO;
               
                cargarDatos();
                break;
        }
    }
    
    
    
    public void guardar(){
        Clientes registro = new Clientes();
        registro.setCodigoCliente(Integer.parseInt(txtCodigoCliente.getText()));
        registro.setNombreCliente(txtNombreC.getText());
        registro.setApellidoCliente(txtApellidoC.getText());
        registro.setNITCliente(txtNitC.getText());
        registro.setTelefonoCliente(txtTelefonoC.getText());
        registro.setDireccionCliente(txtDireccionC.getText());
        registro.setCorreoCliente(txtCorreoC.getText());
        try{
            PreparedStatement procedimiento = Conection.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?, ?, ?, ?, ?, ?, ?)}");          
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3,registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
        }catch(SQLException e){
            e.getErrorCode();
        }
    }
    public void eliminar(){
        switch (tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default :
                if(tbClientes.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar si elimina el Registro",
                            "Eliminar Clientes",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conection.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes)tbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tbClientes.getSelectionModel().getSelectedItem());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null, "Seleccione un elemento.");
                break;
        } 
    }
    public void reporte (){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if(tbClientes.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    txtCodigoCliente.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reportes");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                
            break;
        }
    }
    public void actualizar(){
        try{

            PreparedStatement procedimiento = Conection.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes)tbClientes.getSelectionModel().getSelectedItem();
            registro.setNITCliente(txtNitC.getText());
            registro.setNombreCliente(txtNombreC.getText());
            registro.setApellidoCliente(txtApellidoC.getText()); 
            registro.setTelefonoCliente(txtTelefonoC.getText());
            registro.setDireccionCliente(txtDireccionC.getText());
            registro.setCorreoCliente(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getCodigoCliente());
            procedimiento.setString(2, registro.getNITCliente());
            procedimiento.setString(3,registro.getNombreCliente());
            procedimiento.setString(4, registro.getApellidoCliente());
            procedimiento.setString(5, registro.getTelefonoCliente());
            procedimiento.setString(6, registro.getDireccionCliente());
            procedimiento.setString(7, registro.getCorreoCliente());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void desactivarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
        txtNitC.setEditable(false);
        txtTelefonoC.setEditable(false);

    }
     public void activarControles(){
        txtCodigoCliente.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
        txtNitC.setEditable(true);
        txtTelefonoC.setEditable(true);
     }
     public void limpiarControles(){
        txtCodigoCliente.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
        txtNitC.clear();
        txtTelefonoC.clear();
     }
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
        public void clicRegresar (ActionEvent event){
         if (event.getSource() == btnRegresar){
        escenarioPrincipal.menuPrincipalView();
    }
    }
}
