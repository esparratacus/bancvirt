/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloqueo;

import java.util.HashSet;

/**
 *
 * @author sala_bd
 */
public class Bloqueo {

    public final static String LECTURA = "lectura";
    public final static String ESCRITURA = "escritura";

    private Object objeto;
    private HashSet<Long> propietarios;
    private String tipoBloqueo;

    public void promover() {
        if (tipoBloqueo.equals(LECTURA)) {
            tipoBloqueo = ESCRITURA;
            return;
        }

    }

    public boolean conflicto(String estadoActual, String nuevo, Long tId) {
        System.out.println("Para la transacción "+tId);
        System.out.println("Propietarios");
        for (Long propietario : propietarios) {
            System.out.println("    "+propietario); 
        }
        if (estadoActual == null) {
            System.out.println("Es estado actual es null");
            return false;
        }
        if (propietarios.contains(tId)) {
            System.out.println("Ese es propietario");
            return false;
        }
        if (estadoActual.equals(LECTURA) && nuevo.equals(LECTURA)) {
            System.out.println("Ambos son lectura");
            return false;
        } else {
            System.out.println("else");
            return true;
        }

    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public HashSet<Long> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(HashSet<Long> propietarios) {
        this.propietarios = propietarios;
    }

    public String getTipoBloqueo() {
        return tipoBloqueo;
    }

    public void setTipoBloqueo(String tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }

    public Bloqueo(Object o) {
        objeto = o;
        propietarios = new HashSet<>();

    }

    public synchronized void adquiere(Long idTransacccion, String nuevo) {
        while (conflicto(tipoBloqueo, nuevo, idTransacccion)) {
            System.out.println("Estoy bloqueado, PAILA");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (propietarios.isEmpty()) {
            propietarios.add(idTransacccion);
            tipoBloqueo = nuevo;
        } else if (tipoBloqueo.equals(LECTURA)) {

            if (!propietarios.contains(idTransacccion)) {
                propietarios.add(idTransacccion);
            }
        } else if (tipoBloqueo.equals(LECTURA) && nuevo.equals(ESCRITURA)) {
            propietarios.add(idTransacccion);
            promover();
        }
    }
    
    public synchronized void libera(Long tId){
        propietarios.remove(tId);
        tipoBloqueo = null;
        notifyAll();
    }

}
