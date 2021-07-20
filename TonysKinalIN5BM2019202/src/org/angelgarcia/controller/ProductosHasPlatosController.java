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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.angelgarcia.bd.Conexion;
import org.angelgarcia.bean.Platos;
import org.angelgarcia.bean.Productos;
import org.angelgarcia.bean.ProductosHasPlatos;
import org.angelgarcia.system.MainClass;

public class ProductosHasPlatosController implements Initializable {
    private MainClass escenarioPrincipal;
    private ObservableList <ProductosHasPlatos> listaProductosHasPlatos;
    private ObservableList <Productos> listaProducto;
    private ObservableList <Platos> listaPlatos;

    @FXML
    private TableView tblProductosPlatos;
    @FXML
    private TableColumn colCodigoProducto;
    @FXML
    private TableColumn colCodigoPlato;
    @FXML
    private ComboBox cmbCodigoProducto;
    @FXML
    private ComboBox cmbCodigoPlato;
    
    private void cargarDatos(){
        tblProductosPlatos.setItems(getProductosPlatos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos, Integer>("productos_codigoProducto"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ProductosHasPlatos, Integer>("platos_codigoPlato"));
        cmbCodigoProducto.setItems(getProductos());
        cmbCodigoPlato.setItems(getPlatos());
    }
    
    public void seleccionarElemento(){
        cmbCodigoProducto.getSelectionModel().select(buscarProducto(((ProductosHasPlatos)tblProductosPlatos.getSelectionModel().getSelectedItem()).getProductos_codigoProducto()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((ProductosHasPlatos)tblProductosPlatos.getSelectionModel().getSelectedItem()).getPlatos_codigoPlato()));
    }
    
    public ObservableList<ProductosHasPlatos> getProductosPlatos(){
        ArrayList<ProductosHasPlatos> lista = new ArrayList<ProductosHasPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarProductosHasPlatos()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new ProductosHasPlatos(resultado.getInt("productos_codigoProducto"),
                        resultado.getInt("platos_codigoPlato")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductosHasPlatos=FXCollections.observableArrayList(lista);
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
    
    public ObservableList<Platos> getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarPlatos()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new Platos(resultado.getInt("codigoPlato"),
                        resultado.getInt("cantidad"),
                        resultado.getString("nombrePlato"),
                        resultado.getString("descripcionPlato"),
                        resultado.getDouble("precioPlato"),
                        resultado.getInt("codigoTipoPlato")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPlatos=FXCollections.observableArrayList(lista);
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
    
    public Platos buscarPlatos (int codigoPlato) {
    Platos resultado = null; 
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarPlatos(?)}");
        procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()) {
                resultado = new Platos(registro.getInt("codigoPlato"), 
                        registro.getInt("cantidad"),
                        registro.getString("nombrePlato"),
                        registro.getString("descripcionPlato"),
                        registro.getDouble("precioPlato"),
                        registro.getInt("codigoTipoPlato"));
            }
    }catch(Exception e){
        e.printStackTrace();
    }
    return resultado;
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
