package org.angelgarcia.bean;

import java.util.Date;

public class ServiciosHasEmpleados {
    private int codigoServicioEmpleados;
    private int servicios_codigoServicio;
    private int empleados_codigoEmpleado;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;

    public ServiciosHasEmpleados() {
    }

    public ServiciosHasEmpleados(int codigoServicioEmpleados, int servicios_codigoServicio, int empleados_codigoEmpleado, Date fechaEvento, String horaEvento, String lugarEvento) {
        this.codigoServicioEmpleados = codigoServicioEmpleados;
        this.servicios_codigoServicio = servicios_codigoServicio;
        this.empleados_codigoEmpleado = empleados_codigoEmpleado;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
    }

    public int getCodigoServicioEmpleados() {
        return codigoServicioEmpleados;
    }

    public void setCodigoServicioEmpleados(int codigoServicioEmpleados) {
        this.codigoServicioEmpleados = codigoServicioEmpleados;
    }

    public int getServicios_codigoServicio() {
        return servicios_codigoServicio;
    }

    public void setServicios_codigoServicio(int servicios_codigoServicio) {
        this.servicios_codigoServicio = servicios_codigoServicio;
    }

    public int getEmpleados_codigoEmpleado() {
        return empleados_codigoEmpleado;
    }

    public void setEmpleados_codigoEmpleado(int empleados_codigoEmpleado) {
        this.empleados_codigoEmpleado = empleados_codigoEmpleado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }
    
    
    
}
