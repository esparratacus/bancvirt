/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coordinator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author david
 */
public class Transaction {
    private Long tId;
    private Set<String> recursosAfectados;
    private String username;

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
    }
    
    
    public Transaction(){
        recursosAfectados = new HashSet<>();
        
    }
}
