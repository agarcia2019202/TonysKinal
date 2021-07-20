package org.angelgarcia.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.angelgarcia.controller.AcercaDeController;
import org.angelgarcia.controller.EmpleadosController;
import org.angelgarcia.controller.EmpresasController;
import org.angelgarcia.controller.PlatosController;
import org.angelgarcia.controller.PresupuestosController;
import org.angelgarcia.controller.PrincipalController;
import org.angelgarcia.controller.ProductosController;
import org.angelgarcia.controller.ProductosHasPlatosController;
import org.angelgarcia.controller.ServiciosController;
import org.angelgarcia.controller.ServiciosHasEmpleadosController;
import org.angelgarcia.controller.ServiciosHasPlatosController;
import org.angelgarcia.controller.TipoEmpleadoController;
import org.angelgarcia.controller.TipoPlatoController;

public class MainClass extends Application {
    private final String PAQUETE_VISTA = "/org/angelgarcia/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("TONY'S KINAL");
        vistaPrincipal();
        escenarioPrincipal.show();
//         Parent root = FXMLLoader.load(getClass().getResource("VistaPrincipal.fxml"));
//        
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    public void vistaPrincipal(){
        try{
            PrincipalController vistaPrincipal = (PrincipalController)cambiarEscena("VistaPrincipal.fxml",820, 385);
            vistaPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaEmpresas(){
        try{
            EmpresasController vistaEmpresas = (EmpresasController)cambiarEscena("VistaEmpresas.fxml",857, 475);
            vistaEmpresas.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaTipoEmpleado(){
        try{
            TipoEmpleadoController vistaTipoEmpleado = (TipoEmpleadoController)cambiarEscena("VistaTipoEmpleado.fxml",857, 475);
            vistaTipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaEmpleados(){
        try{
            EmpleadosController vistaEmpleados = (EmpleadosController)cambiarEscena("VistaEmpleados.fxml",857, 475);
            vistaEmpleados.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaServicios(){
        try{
            ServiciosController vistaServicios = (ServiciosController)cambiarEscena("VistaServicios.fxml",857, 475);
            vistaServicios.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaPresupuestos(){
        try{
            PresupuestosController vistaPresupuestos = (PresupuestosController)cambiarEscena("VistaPresupuestos.fxml",857, 475);
            vistaPresupuestos.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaProductos(){
        try{
            ProductosController vistaProductos = (ProductosController)cambiarEscena("VistaProductos.fxml",857, 475);
            vistaProductos.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaTipoPlato(){
        try{
            TipoPlatoController vistaTipoPlato = (TipoPlatoController)cambiarEscena("VistaTipoPlato.fxml",857, 475);
            vistaTipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaPlatos(){
        try{
            PlatosController vistaPlatos = (PlatosController)cambiarEscena("VistaPlatos.fxml",857, 475);
            vistaPlatos.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public void vistaProductosHasPlatos(){
        try{
            ProductosHasPlatosController vistaProductosHasPlatos = (ProductosHasPlatosController)cambiarEscena("VistaProductosHasPlatos.fxml",857, 475);
            vistaProductosHasPlatos.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    } 
    
    public void vistaServiciosHasPlatos(){
        try{
            ServiciosHasPlatosController vistaServiciosHasPlatos = (ServiciosHasPlatosController)cambiarEscena("VistaServiciosHasPlatos.fxml",857, 475);
            vistaServiciosHasPlatos.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    } 
    
    public void vistaServiciosHasEmpleados(){
        try{
            ServiciosHasEmpleadosController vistaServiciosHasEmpleados = (ServiciosHasEmpleadosController)cambiarEscena("VistaServiciosHasEmpleados.fxml",857, 475);
            vistaServiciosHasEmpleados.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    } 
    
    public void vistaAcercaDe(){
        try{
            AcercaDeController vistaAcercaDe = (AcercaDeController)cambiarEscena("VistaAcercaDe.fxml",857, 475);
            vistaAcercaDe.setEscenarioPrincipal(this);
        }catch(Exception e) {
            e.printStackTrace();
        }       
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena (String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML=new FXMLLoader();
        InputStream archivo = MainClass.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(MainClass.class.getResource(PAQUETE_VISTA+fxml));
        escena=new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
        
   
}

