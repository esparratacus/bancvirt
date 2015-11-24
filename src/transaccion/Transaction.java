/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author david
 */
public class Transaction implements Serializable{
    private Long tId;
    private Set<String> recursosAfectados;
    private String username;
    private Long startTime;
    private ArrayList<Action> acciones;

    public ArrayList<Action> getAcciones() {
        return acciones;
    }

    public void setAcciones(ArrayList<Action> acciones) {
        this.acciones = acciones;
    }
    

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Set<String> getRecursosAfectados() {
        return recursosAfectados;
    }

    public void setRecursosAfectados(Set<String> recursosAfectados) {
        this.recursosAfectados = recursosAfectados;
    }

   

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Transaction(Long tId) {
        this.tId = tId;
        recursosAfectados = new HashSet<>();
        startTime = System.currentTimeMillis();
        acciones = new ArrayList<>();
    }
    public Boolean tienePrioridad(Transaction transaccion){
        return startTime <= transaccion.getStartTime();
    }
    
    
    public Transaction(){
        recursosAfectados = new HashSet<>();
        startTime = System.currentTimeMillis();
        acciones = new ArrayList<>();
    }
}
