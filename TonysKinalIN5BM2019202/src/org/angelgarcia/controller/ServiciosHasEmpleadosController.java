package org.angelgarcia.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import org.angelgarcia.bean.Empleados;
import org.angelgarcia.bean.Servicios;
import org.angelgarcia.bean.ServiciosHasEmpleados;
import org.angelgarcia.system.MainClass;

public class ServiciosHasEmpleadosController implements Initializable {
    private MainClass escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <ServiciosHasEmpleados> listaServiciosHasEmpleados;
    private ObservableList <Servicios> listaServicio;
    private ObservableList <Empleados> listaEmpleado;
    private DatePicker fechaEvento;

    @FXML
    private GridPane grpServiciosEmpleados;
    @FXML
    private TextField txtCodigoServiciosEmpleados;
    @FXML
    private TextField txtLugarEvento;
    @FXML
    private TextField txtHoraEvento;
    @FXML
    private ComboBox cmbCodigoEmpleado;
    @FXML
    private GridPane grpFechaEvento;
    @FXML
    private ComboBox cmbCodigoServicio;
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
    private TableColumn colCodigoServicioEmpleado;
    @FXML
    private TableColumn colCodigoServicio;
    @FXML
    private TableColumn colCodigoEmpleado;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colHora;
    @FXML
    private TableColumn colLugar;
    
    public void cargarDatos(){
        tblServicios.setItems(getServiciosEmpleados());
        colCodigoServicioEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("codigoServicioEmpleados"));
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("servicios_codigoServicio"));
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados,Integer>("empleados_codigoEmpleado"));
        colFecha.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, Date> ("fechaEvento"));
        colHora.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, String>("horaEvento"));
        colLugar.setCellValueFactory(new PropertyValueFactory<ServiciosHasEmpleados, String>("lugarEvento"));
        cmbCodigoServicio.setItems(getServicios());
        cmbCodigoEmpleado.setItems(getEmpleados());
        desactivarControles();
    }
    
    public void seleccionarElemento(){
        txtCodigoServiciosEmpleados.setText(String.valueOf(((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicioEmpleados()));
        txtLugarEvento.setText(String.valueOf(((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getLugarEvento()));
        txtHoraEvento.setText(String.valueOf(((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getHoraEvento()));
        fechaEvento.selectedDateProperty().set(((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getFechaEvento());
        cmbCodigoServicio.getSelectionModel().select(buscarServicio(((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getServicios_codigoServicio()));
        cmbCodigoEmpleado.getSelectionModel().select(buscarEmpleado(((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getEmpleados_codigoEmpleado()));
    }
    
    public ObservableList<ServiciosHasEmpleados> getServiciosEmpleados(){
        ArrayList<ServiciosHasEmpleados> lista = new ArrayList<ServiciosHasEmpleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarServiciosHasEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                lista.add(new ServiciosHasEmpleados(resultado.getInt("codigoServicioEmpleados"),
                        resultado.getInt("servicios_codigoServicio"),
                        resultado.getInt("empleados_codigoEmpleado"),
                        resultado.getDate("fechaEvento"),
                        resultado.getString("horaEvento"),
                        resultado.getString("lugarEvento")));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServiciosHasEmpleados=FXCollections.observableArrayList(lista);
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
    
    public Empleados buscarEmpleado (int codigoEmpleado) {
    Empleados resultado = null; 
    try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarEmpleados(?)}");
        procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"), 
                        registro.getInt("numeroEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("direccionEmpleado"),
                        registro.getString("telefonoContacto"),
                        registro.getString("gradoCocinero"),
                        registro.getInt("codigoTipoEmpleado"));
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
                txtCodigoServiciosEmpleados.setEditable(false);
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
        ServiciosHasEmpleados registro = new ServiciosHasEmpleados();
        registro.setServicios_codigoServicio(((Servicios)cmbCodigoServicio.getSelectionModel().getSelectedItem()).getCodigoServicio());
        registro.setEmpleados_codigoEmpleado(((Empleados)cmbCodigoEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
        registro.setFechaEvento(fechaEvento.getSelectedDate());
        registro.setHoraEvento(txtHoraEvento.getText());
        registro.setLugarEvento(txtLugarEvento.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarServiciosHasEmpleados(?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getServicios_codigoServicio());
            procedimiento.setInt(2, registro.getEmpleados_codigoEmpleado());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(4, registro.getHoraEvento());
            procedimiento.setString(5, registro.getLugarEvento());
            procedimiento.execute();
            listaServiciosHasEmpleados.add(registro);
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
                        cmbCodigoServicio.setDisable(true);
                        cmbCodigoEmpleado.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ActualizarServiciosHasEmpleados(?,?,?,?)}");
            ServiciosHasEmpleados registro = (ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem();
            registro.setFechaEvento(fechaEvento.getSelectedDate());
            registro.setHoraEvento(txtHoraEvento.getText());
            registro.setLugarEvento(txtLugarEvento.getText());
            procedimiento.setInt(1, registro.getCodigoServicioEmpleados());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaEvento().getTime()));
            procedimiento.setString(3, registro.getHoraEvento());
            procedimiento.setString(4, registro.getLugarEvento());
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
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarServiciosHasEmpleados(?)}");
                            procedimiento.setInt(1, ((ServiciosHasEmpleados)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicioEmpleados());
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaEvento = new DatePicker(Locale.ENGLISH);
        fechaEvento.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaEvento.getCalendarView().todayButtonTextProperty().set("Today");
        fechaEvento.getCalendarView().setShowWeeks(false);
        fechaEvento.getStylesheets().add("org/angelgarcia/resource/DatePicker.css");
        grpFechaEvento.add(fechaEvento, 0, 0);
        cargarDatos();
    }   
    
    public void desactivarControles(){
        txtCodigoServiciosEmpleados.setEditable(false);
        txtLugarEvento.setEditable(false);
        txtHoraEvento.setEditable(false);
        grpFechaEvento.setDisable(true);
        cmbCodigoServicio.setDisable(true);
        cmbCodigoEmpleado.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoServiciosEmpleados.setEditable(true);
        txtLugarEvento.setEditable(true);
        txtHoraEvento.setEditable(true);
        grpFechaEvento.setDisable(false);
        cmbCodigoServicio.setDisable(false);
        cmbCodigoEmpleado.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoServiciosEmpleados.setText("");
        txtLugarEvento.setText("");
        txtHoraEvento.setText("");
        fechaEvento.selectedDateProperty().set(null);
        cmbCodigoServicio.getSelectionModel().clearSelection();
        cmbCodigoEmpleado.getSelectionModel().clearSelection();
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
