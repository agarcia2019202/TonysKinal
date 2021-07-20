package org.angelgarcia.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.angelgarcia.system.MainClass;

public class AcercaDeController implements Initializable {
    private MainClass escenarioPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
