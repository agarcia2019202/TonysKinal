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
import org.angelgarcia.bean.Servicios;
import org.angelgarcia.reports.GenerarReporte;
import org.angelgarcia.system.MainClass;

public class ServiciosController implements Initializable {
    private MainClass escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Empresa> listaEmpresa;
    private ObservableList <Servicios> listaServicio;
    private DatePicker fechaServicio;
    
    @FXML
    private GridPane grpServicios;
    @FXML
    private GridPane grpFechaServicio;
    @FXML
    private TextField txtCodigoServicio;
    @FXML
    private TextField txtTipoServicio;
    @FXML
    private TextField txtLugarServicio;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtHora;
    @FXML
    private ComboBox cmbCodigoEmpresa;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnReporte;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private TableView tblServicios;
    @FXML
    private TableColumn colCodigoServicio;
    @FXML
    private TableColumn colTipoServicio;
    @FXML
    private TableColumn colLugarServicio;
    @FXML
    private TableColumn colTelefonoServicio;
    @FXML
    private TableColumn colHoraServicio;
    @FXML
    private TableColumn colFechaServicio;
    @FXML
    private TableColumn colCodEmpresa;

    
    //Metodo para cargar los datos a la vista
    public void cargarDatos(){
        tblServicios.setItems(getServicios());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicios, Date> ("fechaServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("tipoServicio"));
        colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("lugarServicio"));
        colTelefonoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("telefonoContacto"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("horaServicio"));
        colCodEmpresa.setCellValueFactory(new PropertyValueFactory<Servicios, Integer>("codigoEmpresa"));
        cmbCodigoEmpresa.setItems(getEmpresa());
        desactivarControles();
    }
    
    public void seleccionarElemento(){
        txtCodigoServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        txtTipoServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio()));
        txtLugarServicio.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio()));
        txtTelefono.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto()));
        txtHora.setText(String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio()));
        fechaServicio.selectedDateProperty().set(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
        cmbCodigoEmpresa.getSelectionModel().select(buscarEmpresas(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
    }
    
        //Metodo para ejecutar el procedimiento y llenar la lista del resultado
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
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                txtCodigoServicio.setEditable(false);
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
        Servicios registro = new Servicios();
        registro.setFechaServicio(fechaServicio.getSelectedDate());
        registro.setTipoServicio(txtTipoServicio.getText());
        registro.setHoraServicio(txtHora.getText());
        registro.setLugarServicio(txtLugarServicio.getText());
        registro.setTelefonoContacto(txtTelefono.getText());
        registro.setCodigoEmpresa(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarServicios(?,?,?,?,?,?)}");
            procedimiento.setDate(1, new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(2, registro.getTipoServicio());
            procedimiento.setString(3, registro.getHoraServicio());
            procedimiento.setString(4, registro.getLugarServicio());
            procedimiento.setString(5, registro.getTelefonoContacto());
            procedimiento.setInt(6, registro.getCodigoEmpresa());
            procedimiento.execute();
            listaServicio.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editar(){
        try{
            switch(tipoDeOperacion){
                case NINGUNO:
                    if(tblServicios.getSelectionModel().getSelectedItem() !=null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarServicios(?,?,?,?,?,?)}");
            Servicios registro = (Servicios)tblServicios.getSelectionModel().getSelectedItem();
            registro.setFechaServicio(fechaServicio.getSelectedDate());
            registro.setTipoServicio(txtTipoServicio.getText());
            registro.setHoraServicio(txtHora.getText());
            registro.setLugarServicio(txtLugarServicio.getText());
            registro.setTelefonoContacto(txtTelefono.getText());
            procedimiento.setInt(1, registro.getCodigoServicio());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaServicio().getTime()));
            procedimiento.setString(3, registro.getTipoServicio());
            procedimiento.setString(4, registro.getHoraServicio());
            procedimiento.setString(5, registro.getLugarServicio());
            procedimiento.setString(6, registro.getTelefonoContacto());
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
                if(tblServicios.getSelectionModel().getSelectedItem() !=null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro de eliminar el registro?","Eliminar Servicio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarServicios(?)}");
                            procedimiento.setInt(1, ((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
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
            case NINGUNO:
                if(tblServicios.getSelectionModel().getSelectedItem() !=null){
                        imprimirReporte(); 
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
                    }
                break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codServicio = Integer.valueOf(((Empresa)cmbCodigoEmpresa.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
        parametros.put("codServicio", codServicio);
        GenerarReporte.mostrarReporte("ReporteServicio.jasper", "Reporte de Servicio", parametros);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaServicio = new DatePicker(Locale.ENGLISH);
        fechaServicio.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaServicio.getCalendarView().todayButtonTextProperty().set("Today");
        fechaServicio.getCalendarView().setShowWeeks(false);
        fechaServicio.getStylesheets().add("org/angelgarcia/resource/DatePicker.css");
        grpFechaServicio.add(fechaServicio, 0, 0);
        cargarDatos();
    
    }

    public void desactivarControles(){
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(false);
        txtLugarServicio.setEditable(false);
        txtTelefono.setEditable(false);
        txtHora.setEditable(false);
        grpFechaServicio.setDisable(true);
        cmbCodigoEmpresa.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(true);
        txtLugarServicio.setEditable(true);
        txtTelefono.setEditable(true);
        txtHora.setEditable(true);
        grpFechaServicio.setDisable(false);
        cmbCodigoEmpresa.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoServicio.setText("");
        txtTipoServicio.setText("");
        txtLugarServicio.setText("");
        txtTelefono.setText("");
        txtHora.setText("");
        fechaServicio.selectedDateProperty().set(null);
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
