/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class Action implements Serializable{
    public final static boolean SUMA = true;
    public final static boolean RESTA = false;
    
    
    private String recursoAfectado;
    boolean operacion;
    Long cantidad;

    public String getRecursoAfectado() {
        return recursoAfectado;
    }

    public void setRecursoAfectado(String recursoAfectado) {
        this.recursoAfectado = recursoAfectado;
    }

    public Action(String recursoAfectado, boolean operacion, Long cantidad) {
        this.recursoAfectado = recursoAfectado;
        this.operacion = operacion;
        this.cantidad = cantidad;
    }
    
    
    
    public boolean isOperacion() {
        return operacion;
    }

    public void setOperacion(boolean operacion) {
        this.operacion = operacion;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
    
}
