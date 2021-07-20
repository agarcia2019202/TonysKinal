package org.angelgarcia.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.angelgarcia.bd.Conexion;
import org.angelgarcia.bean.Empleados;
import org.angelgarcia.bean.TipoEmpleado;
import org.angelgarcia.system.MainClass;

public class EmpleadosController implements Initializable {
    private MainClass escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <TipoEmpleado> listaTipoEmpleado;
    private ObservableList <Empleados> listaEmpleado;
    
    @FXML
    private TextField txtCodigoEmpleado;
    @FXML
    private TextField txtNumeroEmpleado;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtNombre;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtGradoCocinero;
    @FXML
    private ComboBox cmbCodTipoEmpleado;
    @FXML
    private TableColumn colCodEmpleado;
    @FXML
    private TableColumn colNumeroEmpleado;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colGradoCocinero;
    @FXML
    private TableColumn colCodTipoEmpleado;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    
    //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoEmpleado"));
        colNumeroEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("numeroEmpleado"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccionEmpleado"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleados, String>("telefonoContacto"));
        colGradoCocinero.setCellValueFactory(new PropertyValueFactory<Empleados, String>("gradoCocinero"));
        colCodTipoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados, String>("codigoTipoEmpleado"));
        cmbCodTipoEmpleado.setItems(getTipoEmpleado());
        desactivarControles();
    }
    
    public TipoEmpleado buscarTipoEmpleado (int codigoTipoEmpleado) {
    TipoEmpleado resultado = null; 
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarTipoEmpleado(?)}");
        procedimiento.setInt(1, codigoTipoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()) {
                resultado = new TipoEmpleado(registro.getInt("codigoTipoEmpleado"), 
                        registro.getString("descripcion"));
            }
    }catch(Exception e){
        e.printStackTrace();
    }
    return resultado;
    }
    
    public void seleccionarElemento(){
        txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        txtNumeroEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNumeroEmpleado()));
        txtApellidos.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtNombre.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado()));
        txtDireccion.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDireccionEmpleado()));
        txtTelefono.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
        txtGradoCocinero.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getGradoCocinero()));
        cmbCodTipoEmpleado.getSelectionModel().select(buscarTipoEmpleado(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado()));
    }

    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getInt("numeroEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("direccionEmpleado"),
                        resultado.getString("telefonoContacto"),
                        resultado.getString("gradoCocinero"),
                        resultado.getInt("codigoTipoEmpleado")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleado=FXCollections.observableArrayList(lista);
    }
    
        //Metodo para ejecutar el procedimiento y llenar la lista del resultado
    public ObservableList<TipoEmpleado> getTipoEmpleado(){
        ArrayList<TipoEmpleado> lista = new ArrayList<TipoEmpleado>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarTipoEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new TipoEmpleado(resultado.getInt("codigoTipoEmpleado"),
                        resultado.getString("descripcion")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoEmpleado=FXCollections.observableArrayList(lista);
    }

    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                txtCodigoEmpleado.setEditable(false);
                btnNuevo.setText("Guardar");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Empleados registro = new Empleados();
        registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
        registro.setApellidosEmpleado(txtApellidos.getText());
        registro.setNombresEmpleado(txtNombre.getText());
        registro.setDireccionEmpleado(txtDireccion.getText());
        registro.setTelefonoContacto(txtTelefono.getText());
        registro.setGradoCocinero(txtGradoCocinero.getText());
        registro.setCodigoTipoEmpleado(((TipoEmpleado)cmbCodTipoEmpleado.getSelectionModel().getSelectedItem()).getCodigoTipoEmpleado());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarEmpleados(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroEmpleado());
            procedimiento.setString(2, registro.getApellidosEmpleado());
            procedimiento.setString(3, registro.getNombresEmpleado());
            procedimiento.setString(4, registro.getDireccionEmpleado());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setString(6, registro.getGradoCocinero());
            procedimiento.setInt(7, registro.getCodigoTipoEmpleado());
            procedimiento.execute();
            listaEmpleado.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void editar(){
        try{
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        cmbCodTipoEmpleado.setDisable(true);
                        activarControles();
                        tipoDeOperacion = operaciones.EDITAR;  
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                    }
                    break;
                case EDITAR:
                    actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                    limpiarControles();
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
       
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarEmpleados(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNumeroEmpleado(Integer.parseInt(txtNumeroEmpleado.getText()));
            registro.setApellidosEmpleado(txtApellidos.getText());
            registro.setNombresEmpleado(txtNombre.getText());
            registro.setDireccionEmpleado(txtDireccion.getText());
            registro.setTelefonoContacto(txtTelefono.getText());
            registro.setGradoCocinero(txtGradoCocinero.getText());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setInt(2, registro.getNumeroEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setString(4, registro.getNombresEmpleado());
            procedimiento.setString(5, registro.getDireccionEmpleado());
            procedimiento.setString(6, registro.getTelefonoContacto());
            procedimiento.setString(7, registro.getGradoCocinero());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NUEVO;
                break;
            default:
                if(tblEmpleados.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar el registro?","Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarEmpleados(?)}");
                            procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            cargarDatos();
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{JOptionPane.showMessageDialog(null, "Debe seleccionar un registro de la tabla");
                }                
        }
    }
    
    public void generarReporte(){
        switch(tipoDeOperacion){
            case EDITAR:
                desactivarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                limpiarControles();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNumeroEmpleado.setEditable(false);
        txtApellidos.setEditable(false);
        txtNombre.setEditable(false);  
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
        txtGradoCocinero.setEditable(false);
        cmbCodTipoEmpleado.setDisable(true);  
    }
    
    public void activarControles(){
        txtCodigoEmpleado.setEditable(true);
        txtNumeroEmpleado.setEditable(true);
        txtApellidos.setEditable(true);
        txtNombre.setEditable(true);  
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
        txtGradoCocinero.setEditable(true);
        cmbCodTipoEmpleado.setDisable(false);   
    }
    
    public void limpiarControles(){
        txtCodigoEmpleado.setText("");
        txtNumeroEmpleado.setText("");
        txtApellidos.setText("");
        txtNombre.setText(""); 
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtGradoCocinero.setText("");
        cmbCodTipoEmpleado.getSelectionModel().clearSelection(); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    } 
    
    public MainClass getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainClass escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    //Boton de Return
    public void vistaPrincipal(){
        escenarioPrincipal.vistaPrincipal();
    }
    
    @FXML
    private void vistaPrincipal(MouseEvent event) {
        escenarioPrincipal.vistaPrincipal();
    }

}
