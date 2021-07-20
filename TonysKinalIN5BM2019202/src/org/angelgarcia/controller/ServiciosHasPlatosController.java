
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
import org.angelgarcia.bean.Servicios;
import org.angelgarcia.bean.ServiciosHasPlatos;
import org.angelgarcia.system.MainClass;

public class ServiciosHasPlatosController implements Initializable {
    private MainClass escenarioPrincipal;
    private ObservableList <ServiciosHasPlatos> listaServiciosHasPlatos;
    private ObservableList <Servicios> listaServicio;
    private ObservableList <Platos> listaPlatos;

    @FXML
    private TableView tblServiciosPlatos;
    @FXML
    private TableColumn colCodigoServicio;
    @FXML
    private TableColumn colCodigoPlato;
    @FXML
    private ComboBox cmbCodigoServicio;
    @FXML
    private ComboBox cmbCodigoPlato;
    
    private void cargarDatos(){
        tblServiciosPlatos.setItems(getServiciosPlatos());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServiciosHasPlatos, Integer>("servicios_codigoServicio"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<ServiciosHasPlatos, Integer>("platos_codigoPlato"));
        cmbCodigoServicio.setItems(getServicios());
        cmbCodigoPlato.setItems(getPlatos());
    }
    
    public void seleccionarElemento(){
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServiciosHasPlatos)tblServiciosPlatos.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((ServiciosHasPlatos)tblServiciosPlatos.getSelectionModel().getSelectedItem()).getPlatos_codigoPlato()));
    }
    
    public ObservableList<ServiciosHasPlatos> getServiciosPlatos(){
        ArrayList<ServiciosHasPlatos> lista = new ArrayList<ServiciosHasPlatos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarServiciosHasPlatos()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new ServiciosHasPlatos(resultado.getInt("servicios_codigoServicio"),
                        resultado.getInt("platos_codigoPlato")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServiciosHasPlatos=FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Servicios> getServicios(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarServicios()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new Servicios(resultado.getInt("codigoServicio"),
                        resultado.getDate("fechaServicio"),
                        resultado.getString("tipoServicio"),
                        resultado.getString("horaServicio"),
                        resultado.getString("lugarServicio"),
                        resultado.getString("telefonoContacto"),
                        resultado.getInt("codigoEmpresa")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicio=FXCollections.observableArrayList(lista);
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
    
    public Servicios buscarServicio (int codigoServicio) {
    Servicios resultado = null; 
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarServicios(?)}");
        procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()) {
                resultado = new Servicios(registro.getInt("codigoServicio"), 
                        registro.getDate("fechaServicio"),
                        registro.getString("tipoServicio"),
                        registro.getString("horaServicio"),
                        registro.getString("lugarServicio"),
                        registro.getString("telefonoContacto"),
                        registro.getInt("codigoEmpresa"));
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
