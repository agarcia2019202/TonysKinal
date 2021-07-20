
package org.angelgarcia.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.angelgarcia.bd.Conexion;
import org.angelgarcia.bean.Empresa;
import org.angelgarcia.bean.Presupuestos;
import org.angelgarcia.bean.Servicios;
import org.angelgarcia.reports.GenerarReporte;
import org.angelgarcia.system.MainClass;

public class PresupuestosController implements Initializable {
    private MainClass escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Empresa> listaEmpresa;
    private ObservableList <Presupuestos> listaPresupuesto;
    private DatePicker fechaSolicitud;

    @FXML
    private GridPane grpPresupuestos;
    @FXML
    private TextField txtCodigoPresupuesto;
    @FXML
    private TextField txtCantidadPresupuesto;
    @FXML
    private GridPane grpFechaPresupuesto;
    @FXML
    private ComboBox cmbCodigoEmpresa;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private TableView tblPresupuestos;
    @FXML
    private TableColumn colCodigoPresupuesto;
    @FXML
    private TableColumn colFechaSolicitud;
    @FXML
    private TableColumn colCantidadPresupuesto;
    @FXML
    private TableColumn colCodEmpresa;
    
    private void cargarDatos(){
        tblPresupuestos.setItems(getPresupuestos());
        colCodigoPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuestos,Integer>("codigoPresupuesto"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<Presupuestos, Date> ("fechaSolicitud"));
        colCantidadPresupuesto.setCellValueFactory(new PropertyValueFactory<Presupuestos, Double>("cantidadPresupuesto"));
        colCodEmpresa.setCellValueFactory(new PropertyValueFactory<Presupuestos, Integer>("codigoEmpresa"));
        cmbCodigoEmpresa.setItems(getEmpresa());
        desactivarControles();
    }
    
    public void seleccionarElemento(){
        txtCodigoPresupuesto.setText(String.valueOf(((Presupuestos)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto()));
        fechaSolicitud.selectedDateProperty().set(((Presupuestos)tblPresupuestos.getSelectionModel().getSelectedItem()).getFechaSolicitud());
        txtCantidadPresupuesto.setText(String.valueOf(((Presupuestos)tblPresupuestos.getSelectionModel().getSelectedItem()).getCantidadPresupuesto()));
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresas(((Presupuestos)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
    }
    
    public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarEmpresas()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                        resultado.getString("nombreEmpresa"),
                        resultado.getString("direccion"),
                        resultado.getString("telefono")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpresa=FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Presupuestos> getPresupuestos(){
        ArrayList<Presupuestos> lista = new ArrayList<Presupuestos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarPresupuesto()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new Presupuestos(resultado.getInt("codigoPresupuesto"),
                        resultado.getDate("fechaSolicitud"),
                        resultado.getDouble("cantidadPresupuesto"),
                        resultado.getInt("codigoEmpresa")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPresupuesto=FXCollections.observableArrayList(lista);
    }
    
    public Empresa buscarEmpresas (int codigoEmpresa) {
    Empresa resultado = null; 
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarEmpresas(?)}");
        procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()) {
                resultado = new Empresa(registro.getInt("codigoEmpresa"),
                        registro.getString("nombreEmpresa"),
                        registro.getString("direccion"),
                        registro.getString("telefono"));
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
                txtCodigoPresupuesto.setEditable(false);
                btnNuevo.setText("Guardar");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                limpiarControles();
                desactivarControles();
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
        Presupuestos registro = new Presupuestos();
        registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
        registro.setCantidadPresupuesto(Double.parseDouble(txtCantidadPresupuesto.getText()));
        registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarPresupuesto(?,?,?)}");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaSolicitud().getTime()));
            procedimiento.setDouble(2, registro.getCantidadPresupuesto());
            procedimiento.setInt(3, registro.getCodigoEmpresa());
            procedimiento.execute();
            listaPresupuesto.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void editar(){
        try{
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblPresupuestos.getSelectionModel().getSelectedItem() !=null){
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        activarControles();
                        cmbCodigoEmpresa.setDisable(true);
                        tipoDeOperacion = operaciones.EDITAR;   
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                    }
                    break;
                case EDITAR:
                    actualizar();
                    limpiarControles();
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarPresupuesto(?,?,?)}");
            Presupuestos registro = (Presupuestos)tblPresupuestos.getSelectionModel().getSelectedItem();
            registro.setFechaSolicitud(fechaSolicitud.getSelectedDate());
            registro.setCantidadPresupuesto((Double.parseDouble(txtCantidadPresupuesto.getText())));
            procedimiento.setInt(1, registro.getCodigoPresupuesto());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaSolicitud().getTime()));
            procedimiento.setDouble(3, registro.getCantidadPresupuesto());
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
                if(tblPresupuestos.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar el registro?","Eliminar Servicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarPresupuesto(?)}");
                            procedimiento.setInt(1, ((Presupuestos)tblPresupuestos.getSelectionModel().getSelectedItem()).getCodigoPresupuesto());
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
            case NINGUNO:
                if(tblPresupuestos.getSelectionModel().getSelectedItem() !=null){
                        imprimirReporte(); 
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                    }
                break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codEmpresa = Integer.valueOf(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        parametros.put("codEmpresa", codEmpresa);
        GenerarReporte.mostrarReporte("ReportePresupuesto.jasper", "Reporte de Presupuesto", parametros);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaSolicitud = new DatePicker(Locale.ENGLISH);
        fechaSolicitud.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaSolicitud.getCalendarView().todayButtonTextProperty().set("Today");
        fechaSolicitud.getCalendarView().setShowWeeks(false);
        fechaSolicitud.getStylesheets().add("org/angelgarcia/resource/DatePicker.css");
        grpFechaPresupuesto.add(fechaSolicitud, 0, 0);
        cargarDatos();
    } 
    
    public void desactivarControles(){
        txtCodigoPresupuesto.setEditable(false);
        txtCantidadPresupuesto.setEditable(false);
        grpFechaPresupuesto.setDisable(true);
        cmbCodigoEmpresa.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoPresupuesto.setEditable(true);
        txtCantidadPresupuesto.setEditable(true);
        grpFechaPresupuesto.setDisable(false);
        cmbCodigoEmpresa.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoPresupuesto.setText("");
        txtCantidadPresupuesto.setText("");
        fechaSolicitud.selectedDateProperty().set(null);
        cmbCodigoEmpresa.getSelectionModel().clearSelection();
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
