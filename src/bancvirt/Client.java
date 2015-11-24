/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import java.io.Serializable;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author david
 */
public class Client implements Serializable{
    
    
    private String name;
    private char[] pasword;
    private String id;
    private Ahorro ahorro;
    private Corriente corriente;
    private Credito credito;
    private Debito debito;

    public Debito getDebito() {
        return debito;
    }

    public void setDebito(Debito debito) {
        this.debito = debito;
    }
    
    public Ahorro getAhorro() {
        return ahorro;
    }

    public void setAhorro(Ahorro ahorro) {
        this.ahorro = ahorro;
    }

    public Corriente getCorriente() {
        return corriente;
    }

    public void setCorriente(Corriente corriente) {
        this.corriente = corriente;
    }

    public Credito getCredito() {
        return credito;
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPasword() {
        return pasword;
    }

    public void setPasword(char[] pasword) {
        this.pasword = pasword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Client(String name, char[] pasword, String id) {
        super();
        this.name = name;
        this.pasword = pasword;
        this.id = id;
    }
    
    
   
}
