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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import org.angelgarcia.bd.Conexion;
import org.angelgarcia.bean.Productos;
import org.angelgarcia.system.MainClass;

public class ProductosController implements Initializable {
    private MainClass escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Productos> listaProducto;

    @FXML
    private TextField txtCodigoProducto;
    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtCantidadProducto;
    @FXML
    private TableView tblProductos;
    @FXML
    private TableColumn colCodigoProducto;
    @FXML
    private TableColumn colNombreProducto;
    @FXML
    private TableColumn colCantidadProducto;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;

    public void cargarDatos(){
        tblProductos.setItems(getProductos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos,Integer>("codigoProducto"));
        colNombreProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombreProducto"));
        colCantidadProducto.setCellValueFactory(new PropertyValueFactory<Productos,Integer>("cantidad"));
        desactivarControles();
    }
    
    public void seleccionarElemento(MouseEvent event) {
        txtCodigoProducto.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        txtNombreProducto.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getNombreProducto()));
        txtCantidadProducto.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCantidad()));
    }
    
    public ObservableList<Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new Productos(resultado.getInt("codigoProducto"),
                        resultado.getString("nombreProducto"),
                        resultado.getInt("cantidad")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProducto=FXCollections.observableArrayList(lista);
    }
    
    public Productos buscarProducto (int codigoProducto) {
    Productos resultado = null; 
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarProductos(?)}");
        procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()) {
                resultado = new Productos(registro.getInt("codigoProducto"), 
                        registro.getString("nombreProducto"),
                        registro.getInt("cantidad"));
            }
    }catch(Exception e){
        e.printStackTrace();
    }
    return resultado;
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                txtCodigoProducto.setEditable(false);
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
        Productos registro = new Productos();
        registro.setNombreProducto(txtNombreProducto.getText());
        registro.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarProductos(?,?)}");
            procedimiento.setString(1, registro.getNombreProducto());
            procedimiento.setInt(2, registro.getCantidad());
            procedimiento.execute();
            listaProducto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        try{
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblProductos.getSelectionModel().getSelectedItem() !=null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarProductos(?,?,?)}");
            Productos registro = (Productos)tblProductos.getSelectionModel().getSelectedItem();
            registro.setNombreProducto(txtNombreProducto.getText());
            registro.setCantidad(Integer.parseInt(txtCantidadProducto.getText()));
            procedimiento.setInt(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getNombreProducto());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblProductos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar el registro?","Eliminar Servicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarProductos(?)}");
                            procedimiento.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
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

    public void generarReporte() {
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
        txtCodigoProducto.setEditable(false);
        txtNombreProducto.setEditable(false); 
        txtCantidadProducto.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoProducto.setEditable(true);
        txtNombreProducto.setEditable(true);
        txtCantidadProducto.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoProducto.setText("");
        txtNombreProducto.setText("");
        txtCantidadProducto.setText("");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    } 
    
    public MainClass getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(MainClass escenarioPrincipal){
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
 