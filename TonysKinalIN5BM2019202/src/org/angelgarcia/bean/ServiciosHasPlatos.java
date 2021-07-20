package org.angelgarcia.bean;

public class ServiciosHasPlatos {
    private int servicios_codigoServicio;
    private int platos_codigoPlato;

    public ServiciosHasPlatos() {
    }

    public ServiciosHasPlatos(int servicios_codigoServicio, int platos_codigoPlato) {
        this.servicios_codigoServicio = servicios_codigoServicio;
        this.platos_codigoPlato = platos_codigoPlato;
    }

    public int getServicios_codigoServicio() {
        return servicios_codigoServicio;
    }

    public void setServicios_codigoServicio(int servicios_codigoServicio) {
        this.servicios_codigoServicio = servicios_codigoServicio;
    }

    public int getPlatos_codigoPlato() {
        return platos_codigoPlato;
    }

    public void setPlatos_codigoPlato(int platos_codigoPlato) {
        this.platos_codigoPlato = platos_codigoPlato;
    }
    
}
