/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bancvirt;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class Client implements Serializable{
    private String name;
    private char[] pasword;
    private String id;

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
    
    public Client(){
        
    }
}
