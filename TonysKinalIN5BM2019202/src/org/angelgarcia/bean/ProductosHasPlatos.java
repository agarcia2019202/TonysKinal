package org.angelgarcia.bean;

public class ProductosHasPlatos {
    private int productos_codigoProducto;
    private int platos_codigoPlato;

    public ProductosHasPlatos() {
    }

    public ProductosHasPlatos(int productos_codigoProducto, int platos_codigoPlato) {
        this.productos_codigoProducto = productos_codigoProducto;
        this.platos_codigoPlato = platos_codigoPlato;
    }

    public int getProductos_codigoProducto() {
        return productos_codigoProducto;
    }

    public void setProductos_codigoProducto(int productos_codigoProducto) {
        this.productos_codigoProducto = productos_codigoProducto;
    }

    public int getPlatos_codigoPlato() {
        return platos_codigoPlato;
    }

    public void setPlatos_codigoPlato(int platos_codigoPlato) {
        this.platos_codigoPlato = platos_codigoPlato;
    }
    
    
}
