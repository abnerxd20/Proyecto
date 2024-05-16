
package org.abnerdelcid.controller;

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


public class MenuProveedoresController implements Initializable {
        
        @FXML Button btnRegresar;
        private Principal escenarioPrincipal;
        
    private enum operaciones{AGREGAR,ELIMINAR,EDITAR,ACTUALIZAR,CANSELAR,NINGUNO}
        private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        private ObservableList<Clientes> listaClientes;
   
      
        @FXML private TextField txtDireccionP;
        @FXML private TextField txtCorreoP;
        @FXML private TextField txtCodigoP;
        @FXML private TextField txtTelefonoP;
        @FXML private TextField txtNitP;
        @FXML private TextField txtNombreP;
        @FXML private TextField txtApellidoP;
        @FXML private TableView tbProveedores;
        
        @FXML private TableColumn colCodigoP;
        @FXML private TableColumn colNombrep;
        @FXML private TableColumn colNitP;
        @FXML private TableColumn colApellidoP;
        @FXML private TableColumn colDireccionP;
        @FXML private TableColumn colTelefonoP;
         @FXML private TableColumn colCorreoP;
        @FXML Button btnAgregar;
        @FXML Button btnEliminar;
        @FXML Button btnEditar;
        @FXML Button btnReporte;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }   
    
    public void cargarDatos(){
       tbProveedores.setItems(getClientes()); 
       colCodigoP.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("codigoCliente"));
       colNitP.setCellValueFactory(new PropertyValueFactory<Clientes, String>("NITCliente"));
       colNombrep.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreCliente"));
       colApellidoP.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoCliente"));
       colDireccionP.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("direccionCliente"));
       colTelefonoP.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoCliente"));
       colCorreoP.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoCliente"));
       
    }
    
    public void seleccionarElemento(){

        txtCodigoP.setText(String.valueOf(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getCodigoCliente()));

        txtNombreP.setText(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getNombreCliente());

        txtApellidoP.setText(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getApellidoCliente());

        txtNitP.setText(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getNITCliente());

        txtTelefonoP.setText(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getTelefonoCliente());

        txtDireccionP.setText(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getDireccionCliente());

        txtCorreoP.setText(((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getCorreoCliente());

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
        registro.setCodigoCliente(Integer.parseInt(txtCodigoP.getText()));
        registro.setNombreCliente(txtNombreP.getText());
        registro.setApellidoCliente(txtApellidoP.getText());
        registro.setNITCliente(txtNitP.getText());
        registro.setTelefonoCliente(txtTelefonoP.getText());
        registro.setDireccionCliente(txtDireccionP.getText());
        registro.setCorreoCliente(txtCorreoP.getText());
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
                if(tbProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar si elimina el Registro",
                            "Eliminar Clientes",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conection.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes)tbProveedores.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tbProveedores.getSelectionModel().getSelectedItem());
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
                if(tbProveedores.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tbProveedores.setEditable(false);
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
            Clientes registro = (Clientes)tbProveedores.getSelectionModel().getSelectedItem();
            registro.setNITCliente(txtNitP.getText());
            registro.setNombreCliente(txtNombreP.getText());
            registro.setApellidoCliente(txtApellidoP.getText()); 
            registro.setTelefonoCliente(txtTelefonoP.getText());
            registro.setDireccionCliente(txtDireccionP.getText());
            registro.setCorreoCliente(txtCorreoP.getText());
            
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
        txtCodigoP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtCorreoP.setEditable(false);
        txtNitP.setEditable(false);
        txtTelefonoP.setEditable(false);

    }
     public void activarControles(){
        txtCodigoP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtCorreoP.setEditable(true);
        txtNitP.setEditable(true);
        txtTelefonoP.setEditable(true);
     }
     public void limpiarControles(){
        txtCodigoP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtCorreoP.clear();
        txtNitP.clear();
        txtTelefonoP.clear();
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
