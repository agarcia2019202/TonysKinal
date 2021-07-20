package org.angelgarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.angelgarcia.system.MainClass;

public class PrincipalController implements Initializable {
    private MainClass escenarioPrincipal;

    public MainClass getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainClass escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void vistaEmpresas(){
        escenarioPrincipal.vistaEmpresas();
    }
    
    public void vistaTipoEmpleado(){
        escenarioPrincipal.vistaTipoEmpleado();
    }
    
    public void vistaEmpleados(){
        escenarioPrincipal.vistaEmpleados();
    }
    
    public void vistaServicios(){
        escenarioPrincipal.vistaServicios();
    }
    
    public void vistaPresupuestos(){
        escenarioPrincipal.vistaPresupuestos();
    }
    
    public void vistaProductos(){
        escenarioPrincipal.vistaProductos();
    }
    
    public void vistaTipoPlato(){
        escenarioPrincipal.vistaTipoPlato();
    }
    
    public void vistaPlatos(){
        escenarioPrincipal.vistaPlatos();
    }
    
    public void vistaProductosHasPlatos(){
        escenarioPrincipal.vistaProductosHasPlatos();
    }
    
    public void vistaServiciosHasPlatos(){
        escenarioPrincipal.vistaServiciosHasPlatos();
    }
    
    public void vistaServiciosHasEmpleados(){
        escenarioPrincipal.vistaServiciosHasEmpleados();
    }
    
    public void vistaAcercaDe(){
        escenarioPrincipal.vistaAcercaDe();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
